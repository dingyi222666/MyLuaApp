package com.dingyi.myluaapp.build.modules.android.builder

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.*

class AndroidApplicationBuilder(
    private val module: Module
):DefaultBuilder(module) {

    init {

        //Check Manifest exists
        addTask(CheckManifest(module),buildTasks)

        //Exploded AndroidArchive
        addTask(ExplodedAndroidArchive(module),buildTasks)

        //Compile Libraries Resources
        addTask(CompileLibrariesResources(module),buildTasks)

        //Merge Resources
        addTask(MergeResources(module),buildTasks)

        //Generate BuildConfig.java
        addTask(GenerateBuildConfig(module),buildTasks)

        // Merge Manifest


        addTask(MergeManifest(module),buildTasks)

    }

}