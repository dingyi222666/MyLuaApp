package com.dingyi.myluaapp.build.modules.android.builder

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.*
import com.dingyi.myluaapp.build.modules.android.tasks.test.*
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.println
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import java.io.File

class AndroidApplicationBuilder(
    private val module: Module
) : DefaultBuilder(module) {





    init {

        //Check Manifest exists
        addTask(CheckManifest(module),buildTasks)

        //Extract AndroidArchive
        addTask(ExtractAndroidArchiveTest(module),buildTasks)

        //Compile Libraries Resources
        addTask(CompileLibrariesResourcesTest(module),buildTasks)

        //Merge Resources
        addTask(MergeResourcesTest(module), buildTasks)

        // Merge Manifest
        addTask(MergeManifest(module), buildTasks)

        //Process Resources
        addTask(ProcessResourcesTest(module), buildTasks)

        //Generate BuildConfig.java
        addTask(GenerateBuildConfig(module), buildTasks)

        //Generate R.java
        addTask(GenerateRFile(module), buildTasks)

        //Compile Java
        addTask(CompileApplicationJavaTest(module), buildTasks)

        //Transform Class to Dex
        addTask(TransformClassToDexTest(module),buildTasks)

        //Transform Jar to Dex
        addTask(TransformJarToDexTest(module),buildTasks)

        //Merge Ext Dex
        addTask(MergeExtDexTest(module),buildTasks)

        //Merge Assets Resources
        addTask(MergeAssetsResourcesTest(module), buildTasks)

        //Merge jniLibs
        addTask(MergeJniLibs(module), buildTasks)

        //Merge Java Resources
        addTask(MergeJavaResources(module), buildTasks)

        //Merge Library Java Resources
        addTask(MergeLibraryAssetsResources(module), buildTasks)

        //Merge Library JniLibs
        addTask(MergeLibraryJniLibs(module), buildTasks)

        //Merge Library Java Resources
        addTask(MergeLibraryJavaResources(module), buildTasks)

        //Merge Ext Dex
        addTask(DexBuilder(module),buildTasks)

        //Package Apk
        addTask(PackageApk(module),buildTasks)

        //ZipAlign Apk
        addTask(ZipAlignApk(module),buildTasks)

        //Sign Apk
        addTask(SignApk(module),buildTasks)

    }

}