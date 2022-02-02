package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.ExplodedAndroidArchive
import com.dingyi.myluaapp.build.modules.android.tasks.GenerateBuildConfig
import com.dingyi.myluaapp.build.modules.android.tasks.GenerateResValues

class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    init {
        buildTasks.addAll(
            arrayOf(
                GenerateBuildConfig(module),
                GenerateResValues(module),
                ExplodedAndroidArchive(module)
            )
        )
    }


}