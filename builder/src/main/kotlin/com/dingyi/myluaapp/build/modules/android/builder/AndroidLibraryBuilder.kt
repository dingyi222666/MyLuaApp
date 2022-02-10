package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.default.DefaultInputFile
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.tasks.*
import com.dingyi.myluaapp.build.modules.android.tasks.test.*


class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    init {


        //Check Manifest exists
        addTask(CheckManifest(module), buildTasks)

        //Extract AndroidArchive
        addTask(ExtractAndroidArchiveTest(module), buildTasks)

        //Package Resources
        addTask(PackageResourcesTest(module), buildTasks)

        //Compile Libraries Resources
        addTask(CompileLibrariesResourcesTest(module), buildTasks)

        //Merge libraries manifest
        addTask(MergeLibraryManifest(module), buildTasks)

        //Generate BuildConfig.java
        addTask(GenerateBuildConfig(module), buildTasks)

        //Compile Java
        addTask(CompileLibraryJavaTest(module), buildTasks)

        //Transform Class to Dex
        addTask(TransformClassToDexTest(module),buildTasks)

        //Transform Jar to Dex
        addTask(TransformJarToDexTest(module),buildTasks)

        //Merge Ext Dex
        addTask(MergeExtDexTest(module),buildTasks)

        //Merge Assets Resources
        addTask(MergeAssetsResources(module), buildTasks)

        //Merge jniLibs
        addTask(MergeJniLibs(module), buildTasks)

        //Merge Java Resources
        addTask(MergeJavaResources(module), buildTasks)


    }


}