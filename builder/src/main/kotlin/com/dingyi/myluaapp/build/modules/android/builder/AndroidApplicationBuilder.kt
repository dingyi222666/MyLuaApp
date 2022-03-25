package com.dingyi.myluaapp.build.modules.android.builder


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.tasks.build.*
import com.dingyi.myluaapp.build.modules.android.tasks.sync.*
import org.luaj.vm2.LuaBoolean
import org.luaj.vm2.LuaValue

class AndroidApplicationBuilder(
    private val module: Module
) : DefaultBuilder(module) {


    override fun init() {
        addBuildTasks()
        addSyncTasks()
        super.init()
    }

    private fun addSyncTasks() {

        addTask(RefreshConfig(module), syncTasks)

        addTask(ResolveRepositoryUrl(module), syncTasks)

        addTask(ResolveDependencyMavenMetaData(module), syncTasks)

        addTask(ResolveDependencyPom(module), syncTasks)

        addTask(ResolveDependencyResource(module), syncTasks)

        //Extract AndroidArchive
        addTask(ExtractAndroidArchive(module), syncTasks)

    }

    private fun addBuildTasks() {

        var useR8 = false

        //Check Manifest exists
        addTask(CheckManifest(module), buildTasks)


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
                addTask(DexBuilderByR8(module),buildTasks)
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

        //Merge Library Java Resources
        addTask(MergeLibraryAssetsResources(module), buildTasks)

        //Merge Library JniLibs
        addTask(MergeLibraryJniLibs(module), buildTasks)

        //Merge Library Java Resources
        addTask(MergeLibraryJavaResources(module), buildTasks)

        if (!useR8) {

            //Merge Ext Dex
            addTask(DexBuilder(module), buildTasks)
        }

        //Package Apk
        addTask(PackageApk(module), buildTasks)

        //ZipAlign Apk
        addTask(ZipAlignApk(module), buildTasks)

        //Sign Apk
        addTask(SignApk(module), buildTasks)

    }

}