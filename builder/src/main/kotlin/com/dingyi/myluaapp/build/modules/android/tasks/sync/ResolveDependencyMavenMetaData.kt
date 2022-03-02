package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.common.ktx.*
import com.drake.net.Net
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class ResolveDependencyMavenMetaData(private val module: Module) : DefaultTask(module) {


    override val name: String
        get() = this.javaClass.simpleName

    private val repositoryUrlList = mutableListOf<String>()

    private val mavenDependencyMap = mutableMapOf<File, MavenDependency>()

    override suspend fun prepare(): Task.State = withContext(Dispatchers.IO) {

        repositoryUrlList.addAll(
            Gson().fromJson(
                File(module.getProject().getPath().toFile(), ".MyLuaApp/repositories.json")
                    .readText(), getJavaClass<Map<String, List<String>>>()
            )["repositories"].checkNotNull()
        )



        val mavenDependencyList = module
            .getProject()
            .getAllDependency()
            .filterIsInstance<MavenDependency>()
            .filter { it.isDynamicVersion }
            .map {
                it to File(
                    it.getDependencyFile().parentFile?.parentFile,
                    "maven-metadata.xml.md5"
                )
            }


        if (mavenDependencyList.isEmpty()) {
            return@withContext Task.State.SKIPPED
        }


        val isSuccess = kotlin.runCatching {
            withContext(Dispatchers.IO) {
                Net.get("https://www.baidu.com")
                    .apply {
                        setGroup("test")
                    }
                    .execute<String>()
            }

        }.isSuccess


        if (!isSuccess) {
            Net.cancelGroup("test")
            throw CompileError("No Network to download dependency!")
        }

        val size = mavenDependencyList.size / Runtime.getRuntime().availableProcessors() / 3


        launch {
            mavenDependencyList.chunked(
                if (size == 0) {
                    mavenDependencyList.size / 4
                } else size
            ).forEach { list ->
                launch {
                    for (it in list) {
                        downloadMavenMetaDataMD5(it)
                    }
                }
            }
        }.join()

        mavenDependencyList.forEach {
            getTaskInput().addInputFile(it.second)
            mavenDependencyMap[it.second] = it.first
        }

        super.prepare()
    }

    private suspend fun downloadMavenMetaDataMD5(pair: Pair<MavenDependency, File>) {
        var content: String? = null

        for (index in repositoryUrlList.indices) {
            val repositoryUrl = repositoryUrlList[index]

            val url =
                repositoryUrl + pair.first.groupId.replace(
                    ".",
                    "/"
                ) + "/" + pair.first.artifactId.replace(
                    ".",
                    "/"
                ) + "/" + "maven-metadata.xml.md5"

            content = withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    Net.get(url) {
                        setGroup(this@ResolveDependencyMavenMetaData.javaClass.simpleName)
                    }.execute<String>()
                }.onFailure {
                    it.printStackTrace()
                }.getOrNull()
            }

            if (content == null && index == repositoryUrlList.lastIndex) {
                break
            } else if (content != null) {
                break
            }

        }


        val readString = if (!pair.second.exists()) {
            null
        } else {
            pair.second.readText()
        }

        if (readString != content) {
            pair.second.apply {
                kotlin.runCatching {
                    parentFile?.mkdir()
                    createNewFile()
                }.onFailure {
                    throw CompileError("Unable to Create MetaData Check File")
                }
            }
            pair.second.writeText(content.toString())
        }

    }

    private suspend fun downloadMetaData(url: String, file: File): Boolean =
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                Net.get(url)
                    .apply {
                        setDownloadDir(file.parentFile)
                        setDownloadFileName(file.name)
                        setGroup(this@ResolveDependencyMavenMetaData.javaClass.simpleName)
                    }.execute<File>()
            }.onFailure {
                it.printStackTrace()
            }.isSuccess
        }

    override suspend fun run(): Unit = withContext(Dispatchers.IO) {

        launch {

            getTaskInput().let {
                if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
            }.let { it.chunked(it.size / Runtime.getRuntime().availableProcessors()) }
                .forEach { list ->
                    launch {
                        for (it in list) {
                            val mavenDependency = mavenDependencyMap[it.toFile()].checkNotNull()
                            val mavenMetaDataPath =
                                File(
                                    mavenDependency.getDependencyFile().parentFile?.parentFile,
                                    "maven-metadata.xml"
                                )

                            for (repositoryUrl in repositoryUrlList) {
                                val url = repositoryUrl + mavenDependency.groupId.replace(
                                    ".",
                                    "/"
                                ) + "/" + mavenDependency.artifactId.replace(
                                    ".",
                                    "/"
                                ) + "/" + "maven-metadata.xml"
                                val downloadSuccess = downloadMetaData(url, mavenMetaDataPath)
                                module
                                    .getLogger().apply {
                                        debug("\n")

                                        debug("download maven-metadata.xml successful from ${mavenDependency.getDeclarationString()}")
                                    }
                                if (downloadSuccess) {
                                    getTaskInput().bindOutputFile(it, mavenMetaDataPath)
                                    break
                                }
                            }
                        }
                    }

                }


        }.join()



        getTaskInput().snapshot()

        mavenDependencyMap.clear()


        val state = prepare()

        if (state == Task.State.DEFAULT || state == Task.State.INCREMENT) {
            run()
        }

        Net.cancelGroup(this.javaClass.simpleName)

        module
            .getLogger()
            .info("\n")
    }
}