package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask

class CheckManifest(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "CheckManifest"

    override suspend fun prepare(): Task.State {

        getTaskInput()
            .addInputFile(
                module.getFileManager().resolveFile(
                    "src/main/AndroidManifest.xml", module
                )
            )
        return Task.State.DEFAULT
    }

    override suspend fun run() {


        if (!getTaskInput().getAllInputFile()[0].toFile().isFile) {
            throw CompileError("missing AndroidManifest.xml for module:${module.name}")
        }


    }


}