package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.tasks.build.*
import org.luaj.vm2.LuaBoolean
import org.luaj.vm2.LuaValue


class AndroidLibraryBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    override fun init() {
        addBuildTasks()
    }

    private fun addBuildTasks() {


        var useR8 = false

        //Check Manifest exists
        addTask(CheckManifest(module), buildTasks)

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

        val buildConfig = module.getCache().getCache<BuildConfig?>(
            "${
                module.getProject().getMainModule().name
            }_build_config"
        )

        if (buildConfig != null) {

            val minifyEnabledValue = module
                .getMainBuilderScript()
                .get(
                    "android.buildTypes.${
                        buildConfig.buildVariants
                    }.minifyEnabled"
                )

            if (minifyEnabledValue is LuaBoolean && minifyEnabledValue.booleanValue()) {
                useR8 = true
            }
        }

        if (!useR8) {

            //Transform Class to Dex
            addTask(TransformClassToDex(module), buildTasks)

            //Transform Jar to Dex
            addTask(TransformJarToDex(module), buildTasks)

            //Merge Ext Dex
            addTask(MergeExtDex(module), buildTasks)
        }


        //Merge Assets Resources
        addTask(MergeAssetsResources(module), buildTasks)

        //Merge jniLibs
        addTask(MergeJniLibs(module), buildTasks)

        //Merge Java Resources
        addTask(MergeJavaResources(module), buildTasks)


    }


}