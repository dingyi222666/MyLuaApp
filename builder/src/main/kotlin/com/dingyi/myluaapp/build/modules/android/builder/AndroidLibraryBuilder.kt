package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.*

class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    init {

        //Check Manifest exists
        addTask(CheckManifest(module), buildTasks)

        //Exploded AndroidArchive
        addTask(ExtractAndroidArchive(module), buildTasks)

        //Package Resources
        addTask(PackageResources(module), buildTasks)


        //Compile Libraries Resources
        addTask(CompileLibrariesResources(module), buildTasks)


        //Merge libraries manifest
        addTask(MergeLibraryManifest(module), buildTasks)

        //Generate BuildConfig.java
        addTask(GenerateBuildConfig(module), buildTasks)

        //Compile Java
        addTask(CompileLibraryJava(module), buildTasks)

        //Merge Assets Resources
        addTask(MergeAssetsResources(module), buildTasks)

        //Merge jniLibs
        addTask(MergeJniLibs(module), buildTasks)

        //Merge Java Resources
        addTask(MergeJavaResources(module), buildTasks)


    }


}