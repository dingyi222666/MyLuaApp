package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.tasks.*
import com.dingyi.myluaapp.build.modules.android.tasks.test.ExtractAndroidArchiveTest
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile

class AndroidLibraryBuilder(
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

        addTask(checkManifestTask,buildTasks)
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

        com.dingyi.myluaapp.common.kts.println(
            extractAndroidArchive
                .getTaskInput()
        )

        addTask(extractAndroidArchive,buildTasks)
    }

    init {

        //Check Manifest exists
        addCheckManifestTask()

        //Extract AndroidArchive
        addExtractAndroidArchiveTask()

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


    }


}