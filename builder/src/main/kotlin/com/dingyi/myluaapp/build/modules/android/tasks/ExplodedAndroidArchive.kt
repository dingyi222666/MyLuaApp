package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.UnzipParameters
import net.lingala.zip4j.util.Zip4jUtil
import java.io.File

class ExplodedAndroidArchive(private val module: Module) : Task {
    override val name: String
        get() = "ExplodedAndroidArchive"

    private lateinit var mavenDependencyList: List<MavenDependency>

    override suspend fun prepare(): Task.State {

        val mavenDependencyList = module.getDependencies().mapNotNull {
            return@mapNotNull if (it is MavenDependency) {
                it
            } else {
                null
            }
        }.flatMap {
            mutableSetOf(it)
                .apply { it.getDependencies()?.let { it1 -> addAll(it1) } }
        }.toSet()

        if (mavenDependencyList.isEmpty()) {
            return Task.State.SKIPPED
        }

        val newMavenDependencyList = mavenDependencyList.filterNot {
            val explodedDir =
                "${Paths.explodedAarDir}${File.separator}${it.getDeclarationString().toMD5()}"

            val file = explodedDir.toFile()

            file.isDirectory && (file.listFiles()?.size ?: 0) > 0

        }


        this.mavenDependencyList = newMavenDependencyList

        return when {
            newMavenDependencyList.isEmpty() -> Task.State.`UP-TO-DATE`
            mavenDependencyList.size > newMavenDependencyList.size -> Task.State.INCREMENT
            mavenDependencyList.size == newMavenDependencyList.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }

    override suspend fun run() = withContext(Dispatchers.IO) {
        launch {
            mavenDependencyList.forEach {
                launch {
                    withContext(Dispatchers.IO) {
                        val file = ZipFile(
                            it.getDependencyFile()
                        )
                        val explodedDir =
                            "${Paths.explodedAarDir}${File.separator}${
                                it.getDeclarationString().toMD5()
                            }"

                        runCatching {

                            file.extractAll(explodedDir)

                            file.close()
                        }.onFailure {
                            System.err.println(it)
                        }
                    }

                }
            }
        }.join()


    }


}