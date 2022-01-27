package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task

class GenerateDebugResValues(
    private val targetModule: Module
):Task {
    override val name: String
        get() = "CompileLibraryResources"



    override fun prepare() {

    }

    override suspend fun run() {

    }
}