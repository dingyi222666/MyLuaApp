package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CleanTask(applyModule: Module) : DefaultTask(applyModule) {

    override val name: String
        get() = this.javaClass.simpleName

    override suspend fun prepare(): Task.State = withContext(Dispatchers.IO) {

        val allFile = applyModule
            .getFileManager()
            .resolveFile("build", applyModule)
            .walkBottomUp()
            .toList()

        if (allFile.isEmpty()) {
            Task.State.`UP-TO-DATE`
        } else Task.State.DEFAULT
    }

    override suspend fun run(): Unit = withContext(Dispatchers.IO) {
        applyModule
            .getFileManager()
            .resolveFile("build", applyModule)
            .deleteRecursively()

        if (applyModule == applyModule.getProject()
                .getMainModule()
        ) {
            applyModule
                .getProject()
                .run {
                    File(this.getPath(), "build")
                }
                .deleteRecursively()
        }
    }
}