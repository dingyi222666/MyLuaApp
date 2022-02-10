package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.TaskInput
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.default.DefaultTaskInput

class CheckManifest(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "CheckManifest"

    override suspend fun prepare(): Task.State {
        return Task.State.DEFAULT
    }


    override suspend fun run() {


        if (!getTaskInput().getAllInputFile()[0].getPath().isFile) {
            throw CompileError("missing AndroidManifest.xml for module:${module.name}")
        }


    }


}