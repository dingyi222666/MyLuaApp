package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.GenerateResValues

class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    init {
        buildTasks.addAll(
            arrayOf(
                GenerateResValues(module)
            )
        )
    }


}