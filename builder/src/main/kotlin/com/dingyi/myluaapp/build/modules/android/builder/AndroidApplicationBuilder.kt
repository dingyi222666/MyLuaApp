package com.dingyi.myluaapp.build.modules.android.builder

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.*
import com.dingyi.myluaapp.build.modules.android.tasks.test.*
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.println
import com.dingyi.myluaapp.common.kts.toFile

class AndroidApplicationBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    private fun addCheckManifestTask() {
        val checkManifestTask = CheckManifest(module)
        checkManifestTask.getTaskInput()
            .addInputFile(
                module.getFileManager().resolveFile(
                    "src/main/AndroidManifest.xml", module
                )
            )

    }

    private fun addExtractAndroidArchiveTask() {
        val extractAndroidArchive = ExtractAndroidArchiveTest(module)

        extractAndroidArchive.getTaskInput()
            .let { input ->
               module.getDependencies()
                    .flatMap {
                        it.getDependenciesFile()
                    }.filter {
                        it.isFile and it.name.endsWith("aar")
                    }.forEach {
                        input.addInputFile(it)
                    }
            }

        extractAndroidArchive.getTaskInput()
            .addOutputDirectory(Paths.extractAarDir.toFile())

        println(extractAndroidArchive
            .getTaskInput())

        addTask(extractAndroidArchive,buildTasks)
    }

    init {

        //Check Manifest exists
        addCheckManifestTask()

        //Extract AndroidArchive
        addExtractAndroidArchiveTask()

        //Compile Libraries Resources
        addTask(CompileLibrariesResources(module), buildTasks)

        //Merge Resources
        addTask(MergeResources(module), buildTasks)


        // Merge Manifest
        addTask(MergeManifest(module), buildTasks)

        //Process Resources
        addTask(ProcessResources(module), buildTasks)

        //Generate BuildConfig.java
        addTask(GenerateBuildConfig(module), buildTasks)

        //Generate R.java
        addTask(GenerateRFile(module), buildTasks)

        //Compile Java
        addTask(CompileApplicationJava(module), buildTasks)

        //Transform Class to Dex
        addTask(TransformClassToDex(module),buildTasks)

        //Transform Jar to Dex
        addTask(TransformJarToDex(module),buildTasks)

        //Merge Ext Dex
        addTask(MergeExtDex(module),buildTasks)

        //Merge Assets Resources
        addTask(MergeAssetsResources(module), buildTasks)

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