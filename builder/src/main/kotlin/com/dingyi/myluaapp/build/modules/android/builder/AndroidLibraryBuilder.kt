package com.dingyi.myluaapp.build.modules.android.builder

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.default.DefaultModule
import com.dingyi.myluaapp.build.modules.android.tasks.Hello
import com.dingyi.myluaapp.build.modules.android.tasks.World

class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    init {
        buildTasks.addAll(
            arrayOf(
                Hello(module),
                World(module)
            )
        )
    }


}