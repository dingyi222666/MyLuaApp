package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.ktx.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File

class ExtractAndroidArchive(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "ExtractAndroidArchive"


    override suspend fun prepare(): Task.State {

        val mavenDependencyList = module
            .getProject()
            .getAllDependency()
            .map {
                if (it is MavenDependency) {
                    module.getMavenRepository()
                        .getDependency(it.getDeclarationString())
                } else it
            }
            .toMutableList()
            .filterDependency()
            .flatMap {
                it.getDependenciesFile()
            }.filter {
                it.isFile and it.name.endsWith("aar")
            }

        if (mavenDependencyList.isEmpty()) {
            return Task.State.SKIPPED
        }

        mavenDependencyList.forEach {
            getTaskInput()
                .addInputFile(it)
        }

        return super.prepare()
    }

    override suspend fun run() = withContext(Dispatchers.IO) {
        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach {
            withContext(Dispatchers.IO) {
                val file = ZipFile(it.toFile())

                val explodedDir =
                    "${Paths.extractAarDir}${File.separator}${
                        it.toFile().path.toMD5()
                    }"

                runCatching {
                    file.extractAll(explodedDir)
                    file.close()
                    getTaskInput()
                        .bindOutputFile(it, explodedDir.toFile())
                }.onFailure {
                    module
                        .getLogger()
                        .warning("w:Failed to extract dependency(${file.file.name}):${it.message}")
                    System.err.println(it)
                }
            }
        }

        getTaskInput().snapshot()

    }

    private fun addToDependencyList(
        dependency: MavenDependency,
        dependencyList: MutableSet<Dependency>
    ) {
        if (dependencyList.add(dependency)) {
            dependency.getDependencies()?.forEach { it ->
                addToDependencyList(it, dependencyList)
            }
        }
    }


    private fun MutableList<Dependency>.filterDependency(): List<Dependency> {
        val dependencyList = mutableSetOf<Dependency>()

        //unpack dependency
        this.forEach { dependency ->
            if (dependency is MavenDependency) {
                addToDependencyList(dependency, dependencyList)
            }
        }

        this.clear()
        this.addAll(dependencyList)
        dependencyList.clear()

        return this

    }

}