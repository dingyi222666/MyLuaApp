package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask

class MergeLibraryJavaResources(
    private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = "MergeLibraryJavaResources"



}