package com.dingyi.myluaapp.build.modules.android.tasks.build

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


    override suspend fun prepare(): Task.State {
        getTaskInput()
            .let { input ->
                module.getDependencies()
                    .flatMap {
                        it.getDependenciesFile()
                    }.filter {
                        it.isFile and it.name.endsWith("aar")
                    }.forEach {
                        input.addInputFile(it)
                    }
            }

        getTaskInput()
            .addOutputDirectory(Paths.extractAarDir.toFile())

        return super.prepare()
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach {
            val file = ZipFile(it.toFile())

            val explodedDir =
                "${getTaskInput().getOutputDirectory()[0]}${File.separator}${
                    it.toFile().path.toMD5()
                }"

            runCatching {
                file.extractAll(explodedDir)
                file.close()
            }.onFailure {
                module
                    .getLogger()
                    .warning("w:Failed to extract dependency(${file.file.name}):${it.message}")
                System.err.println(it)
            }

            getTaskInput()
                .bindOutputFile(it, explodedDir.toFile())

        }

        getTaskInput().snapshot()

    }


}