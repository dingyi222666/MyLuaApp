package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File

//TODO:Add to sync tasks
class ExtractAndroidArchive(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "ExtractAndroidArchive"

    private lateinit var mavenDependencyList: List<File>


    override suspend fun prepare(): Task.State {

        val mavenDependencyList = module.getDependencies()
            .flatMap {
                it.getDependenciesFile()
            }.filter {
                it.isFile and it.name.endsWith("aar")
            }

        if (mavenDependencyList.isEmpty()) {
            return Task.State.SKIPPED
        }


        val incrementalDependencyList =
            mavenDependencyList.filter {
                module.getFileManager()
                    .getSnapshotManager()
                    .equalsAndSnapshot(it)
                    .not() or checkExplodedFiles(it)
            }



        this.mavenDependencyList = incrementalDependencyList

        return when {

            incrementalDependencyList.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalDependencyList.size > mavenDependencyList.size -> Task.State.INCREMENT
            incrementalDependencyList.size == mavenDependencyList.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }

    }

    private fun checkExplodedFiles(file: File): Boolean {
        return ("${Paths.extractAarDir}${File.separator}${
            file.path.toMD5()
        }".toFile().listFiles()?.size ?: 0) <= 1

    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        mavenDependencyList.forEach {
                    withContext(Dispatchers.IO) {
                        val file = ZipFile(it)

                        val explodedDir =
                            "${Paths.extractAarDir}${File.separator}${
                                it.path.toMD5()
                            }"

                        runCatching {
                            file.extractAll(explodedDir)
                            file.close()
                        }.onFailure {
                            module
                                .getLogger()
                                .warning("Failed to extract dependency(${file.file.name}):${it.message}")
                            System.err.println(it)
                        }
                    }
            }


    }


}