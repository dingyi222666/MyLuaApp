package com.dingyi.myluaapp.build.modules.android.tasks.test

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
import kotlin.properties.Delegates

//TODO:Add to sync tasks
class ExtractAndroidArchiveTest(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "ExtractAndroidArchive"

    private var isIncremental by Delegates.notNull<Boolean>()

    override suspend fun prepare(): Task.State {


        if (getTaskInput().getAllInputFile().isEmpty()) {
            return Task.State.SKIPPED
        }

        isIncremental = getTaskInput().isIncremental()

        return if (isIncremental) {
            if (getTaskInput().getIncrementalInputFile().isEmpty()) {
                Task.State.`UP-TO-DATE`
            } else {
                Task.State.INCREMENT
            }
        } else {
            Task.State.DEFAULT
        }

    }


    override suspend fun run() = withContext(Dispatchers.IO) {

        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach {
            val file = ZipFile(it.getPath())

            val explodedDir =
                "${getTaskInput().getOutputDirectory()[0]}${File.separator}${
                    it.getPath().path.toMD5()
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

            getTaskInput()
                .bindOutputFile(it, explodedDir.toFile())

        }

        getTaskInput().snapshot()

    }


}