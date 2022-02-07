package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultTask

class PrepareMergeJavaResources(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = "PrepareMergeJavaResources"


}