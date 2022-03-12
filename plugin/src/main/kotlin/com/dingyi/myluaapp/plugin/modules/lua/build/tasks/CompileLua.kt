package com.dingyi.myluaapp.plugin.modules.lua.build.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultTask

class CompileLua(
    private val module: Module
): DefaultTask(module) {
    override suspend fun run() {
        TODO("Not yet implemented")
    }
}