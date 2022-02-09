package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.util.getSHA256
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MergeLibraryJavaResources(private val applicationModule: Module) :
    DefaultTask(applicationModule) {


    override val name: String
        get() = javaClass.simpleName

    private val libraryMergeAssetsResourceDirectory: String
        get() = "build/intermediates/library_java_res/$buildVariants"

    private val mergeAssetsOutputDirectory: String
        get() = "build/intermediates/merged_java_res/$buildVariants"


    private val mergeAssetsResourceFiles = mutableListOf<Pair<File, File>>()

    private lateinit var buildVariants: String


    override suspend fun prepare(): Task.State {

        buildVariants =
            applicationModule.getCache()
                .getCache<BuildConfig>("${applicationModule.name}_build_config").buildVariants

        val allModule =
            applicationModule
                .getProject()
                .getAllModule()


        if (allModule.isEmpty()) {
            return Task.State.SKIPPED
        }

        val allMergeAssetsResourceFiles = mutableListOf<Pair<File, File>>()

        val applicationModuleLibraryMergeDirectory = applicationModule.getFileManager()
            .resolveFile(libraryMergeAssetsResourceDirectory, applicationModule)

        allModule.forEach { module ->
            val libraryMergeDirectory = module.getFileManager()
                .resolveFile(libraryMergeAssetsResourceDirectory, module)

            libraryMergeDirectory
                .walkBottomUp()
                .filter { it.isFile }
                .forEach {

                    if (module != applicationModule) {
                        val applicationModuleFile =
                            File(
                                applicationModuleLibraryMergeDirectory,
                                it.path.substring(libraryMergeDirectory.path.length + 1)
                            )

                        if (applicationModuleFile.isFile) {
                            if (applicationModuleFile.getSHA256() != it.getSHA256()) {
                                throw CompileError("Duplicate Java Resource File ${it.name}(${it.path}) found in modules (${applicationModule.name},${module.name})")
                            }
                        }
                    }

                    allMergeAssetsResourceFiles.add(libraryMergeDirectory to it)

                }
        }


        if (allMergeAssetsResourceFiles.isEmpty()) {
            return Task.State.SKIPPED
        }


        val targetMergeAssetsResourceDirectory = applicationModule
            .getFileManager()
            .resolveFile(mergeAssetsOutputDirectory, applicationModule)

        val incrementalAssetsResourceFiles = allMergeAssetsResourceFiles
            .filter {
                val targetFile = File(
                    targetMergeAssetsResourceDirectory,
                    it.second.path.substring(it.first.path.length + 1)
                )


                if (targetFile.isFile) targetFile.getSHA256() != it.second.getSHA256() else true

            }

        mergeAssetsResourceFiles.addAll(incrementalAssetsResourceFiles)

        return when {
            incrementalAssetsResourceFiles.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalAssetsResourceFiles.size < allMergeAssetsResourceFiles.size -> Task.State.INCREMENT
            incrementalAssetsResourceFiles.size == allMergeAssetsResourceFiles.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }

    override suspend fun run() = withContext(Dispatchers.IO) {
        val targetMergeAssetsResourceDirectory = applicationModule
            .getFileManager()
            .resolveFile(mergeAssetsOutputDirectory, applicationModule)

        launch {
            mergeAssetsResourceFiles.forEach {
                launch {
                    val targetFile = File(
                        targetMergeAssetsResourceDirectory,
                        it.second.path.substring(it.first.path.length + 1)
                    )
                    targetFile.parentFile?.mkdirs()
                    //TODO:Duplicate File
                    if (!targetFile.isFile) {
                        it.second.copyTo(targetFile)
                    }
                }
            }
        }.join()


    }
}