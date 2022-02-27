package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.util.ComparableVersion
import com.dingyi.myluaapp.build.util.getMD5
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.drake.net.Net
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ResolveDependencyResource(private val module: Module) : DefaultTask(module) {


    private val mavenDependencyMap = mutableMapOf<File, MavenDependency>()

    private val repositoryUrlList = mutableListOf<String>()

    override val name: String
        get() = this.javaClass.simpleName

    override suspend fun prepare(): Task.State {


        repositoryUrlList.addAll(
            Gson().fromJson(
                File(module.getProject().getPath().toFile(), ".MyLuaApp/repositories.json")
                    .readText(), getJavaClass<Map<String, List<String>>>()
            )["repositories"].checkNotNull()
        )

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

        val dependencies = module
            .getProject()
            .getAllDependency()
            .filterIsInstance<MavenDependency>()
            .map {
                module.getMavenRepository()
                    .getDependency(it.getDeclarationString())
            }
            .toMutableList()
            .filterDependency()



        dependencies.map { dependency ->
            val file = dependency.getDependencyFile().let {
                File(it.parentFile, it.name + ".md5")
            }
            getTaskInput().addInputFile(file)
            file to dependency
        }.let {
            mavenDependencyMap.putAll(it)
        }



        return super.prepare()
    }

    override suspend fun run(): Unit = withContext(Dispatchers.IO) {


        launch {
            getTaskInput().let {
                if (isIncremental)
                    it.getIncrementalInputFile()
                else it.getAllInputFile()
            }.let {
                val size = it.size / Runtime.getRuntime().availableProcessors()
                it.chunked(if (size == 0) it.size else size)
            }.forEach { list ->
                launch {
                    for (inputFile in list) {
                        val outputFile =
                            downloadResource(
                                mavenDependencyMap[inputFile.toFile()].checkNotNull(),
                                inputFile.toFile()
                            )
                        if (outputFile != null) {
                            getTaskInput()
                                .bindOutputFile(inputFile, outputFile)
                        }
                    }
                }
            }


        }.join()


        Net.cancelGroup(this.javaClass.simpleName)

        getTaskInput().snapshot()

        mavenDependencyMap.clear()

        module
            .getLogger()
            .info("\n")
    }

    private fun getMavenDependencyUrl(
        repositoryUrl: String,
        mavenDependency: MavenDependency,
        name: String
    ): String {
        return repositoryUrl + mavenDependency.groupId.replace(
            ".",
            "/"
        ) + "/" + mavenDependency.artifactId.replace(
            ".",
            "/"
        ) + "/" + mavenDependency.versionName + "/" + name
    }

    private suspend fun downloadMD5File(mavenDependency: MavenDependency, file: File): Unit =
        withContext(Dispatchers.IO) {
            for (repositoryUrl in repositoryUrlList) {
                val url = getMavenDependencyUrl(
                    repositoryUrl, mavenDependency,
                    mavenDependency.getDependencyFile().name + ".md5"
                )
                file.parentFile?.mkdirs()

                val success = kotlin.runCatching {
                    Net.get(url)
                        .apply {
                            setDownloadDir(file.parentFile)
                            setDownloadFileName(file.name)
                            setGroup(this@ResolveDependencyResource.javaClass.simpleName)
                        }.execute<File>()
                }.onFailure {
                    it.printStackTrace()
                }.isSuccess

                if (success) break
            }
        }

    private suspend fun downloadResource(mavenDependency: MavenDependency, md5File: File): File? =
        withContext(Dispatchers.IO) {

            if (mavenDependency.getDependencyFile().exists() && md5File.exists()) {
                if (mavenDependency.getDependencyFile().getMD5() == md5File.readText()) {
                    return@withContext mavenDependency.getDependencyFile()
                }
            }

            downloadMD5File(mavenDependency, md5File)
            var result: File? = null
            for (repositoryUrl in repositoryUrlList) {
                val url = getMavenDependencyUrl(
                    repositoryUrl, mavenDependency,
                    mavenDependency.getDependencyFile().name
                )


                val success = kotlin.runCatching {
                    Net.get(url)
                        .apply {
                            setDownloadDir(mavenDependency.getDependencyFile().parentFile)
                            setDownloadFileName(mavenDependency.getDependencyFile().name)
                            setGroup(this@ResolveDependencyResource.javaClass.simpleName)
                        }.execute<File>()
                }.onFailure {
                    it.printStackTrace()
                }.onSuccess {
                    result = it
                }.isSuccess

                if (success) {
                    module
                        .getLogger().apply {
                            debug("\n")
                            debug("download ${mavenDependency.type} file successful from ${mavenDependency.getDeclarationString()}")
                        }
                    break
                }

                if (repositoryUrlList.indexOf(repositoryUrl) == repositoryUrl.lastIndex && result == null) {
                    error("")
                }

            }
            result
        }


    private fun addToDependencyList(
        dependency: MavenDependency,
        dependencyList: MutableSet<MavenDependency>
    ) {
        if (dependencyList.add(dependency)) {
            dependency.getDependencies()?.forEach { it ->
                addToDependencyList(it, dependencyList)
            }
        }
    }


    private fun MutableList<MavenDependency>.filterDependency(): List<MavenDependency> {
        val dependencyList = mutableSetOf<MavenDependency>()

        //unpack dependency
        this.forEach { dependency ->
            addToDependencyList(dependency, dependencyList)
        }

        clear()
        addAll(dependencyList)
        dependencyList.clear()

        return this

    }
}