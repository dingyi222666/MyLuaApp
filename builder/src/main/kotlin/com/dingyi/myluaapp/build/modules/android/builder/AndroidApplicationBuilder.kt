package com.dingyi.myluaapp.build.modules.android.builder

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.Hello
import com.dingyi.myluaapp.build.modules.android.tasks.World

class AndroidApplicationBuilder(
    private val module:Module
):DefaultBuilder(module) {

    init {
        buildTasks.addAll(
            arrayOf(

                World(module),
                        Hello(module),
            )
        )
    }

}