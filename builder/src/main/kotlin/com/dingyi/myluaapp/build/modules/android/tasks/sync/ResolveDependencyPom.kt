package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.dependency.EmptyMavenDependency
import com.dingyi.myluaapp.build.dependency.LocalMavenDependency
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

class ResolveDependencyPom(private val module: Module) : DefaultTask(module) {

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
                if (dependency.packaging == "default") File(
                    it,
                    dependency.artifactId + '-' + dependency.versionName + ".pom.md5"
                ) else
                    File(
                        it.parentFile,
                        dependency.artifactId + '-' + dependency.versionName + ".pom.md5"
                    )
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
                            downloadPom(
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

        getTaskInput().snapshot()


        mavenDependencyMap.clear()


        val state = prepare()

        if (state == Task.State.DEFAULT || state == Task.State.INCREMENT) {
            run()
        }

        Net.cancelGroup(this.javaClass.simpleName)


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
                    mavenDependency.artifactId + "-" + mavenDependency.versionName + ".pom.md5"
                )
                file.parentFile?.mkdirs()
                val success = kotlin.runCatching {
                    Net.get(url)
                        .apply {
                            setDownloadDir(file.parentFile)
                            setDownloadFileName(file.name)
                            setGroup(this@ResolveDependencyPom.javaClass.simpleName)
                        }.execute<File>()
                }.onFailure {
                    it.printStackTrace()
                }.isSuccess

                if (success) break
            }
        }

    private suspend fun downloadPom(mavenDependency: MavenDependency, md5File: File): File? =
        withContext(Dispatchers.IO) {

            val pomFile = if (mavenDependency.getDependencyFile().isDirectory) File(
                mavenDependency.getDependencyFile(),
                mavenDependency.artifactId + '-' + mavenDependency.versionName + ".pom"
            ) else
                File(
                    mavenDependency.getDependencyFile().parentFile,
                    mavenDependency.artifactId + '-' + mavenDependency.versionName + ".pom"
                )

            if (pomFile.exists() && md5File.exists()) {
                if (pomFile.getMD5() == md5File.readText()) {
                    return@withContext pomFile
                }
            }

            downloadMD5File(mavenDependency, md5File)

            var result: File? = null


            for (repositoryUrl in repositoryUrlList) {
                val url = getMavenDependencyUrl(
                    repositoryUrl, mavenDependency,
                    mavenDependency.artifactId + "-" + mavenDependency.versionName + ".pom"
                )


                pomFile.parentFile?.mkdirs()

                val success = kotlin.runCatching {
                    Net.get(url)
                        .apply {
                            setDownloadDir(pomFile.parentFile)
                            setDownloadFileName(pomFile.name)
                            setGroup(this@ResolveDependencyPom.javaClass.simpleName)
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
                            debug("download pom file successful from ${mavenDependency.getDeclarationString()}")
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

        this.clear()
        this.addAll(dependencyList)
        dependencyList.clear()

        return this

    }
}
