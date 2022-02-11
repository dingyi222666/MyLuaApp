package com.dingyi.myluaapp.build.modules.android.tasks.test

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

class MergeLibraryJavaResourcesTest(private val applicationModule: Module) :
    DefaultTask(applicationModule) {


    override val name: String
        get() = javaClass.simpleName

    private val libraryMergeAssetsResourceDirectory: String
        get() = "build/intermediates/library_java_res/$buildVariants"

    private val mergeAssetsOutputDirectory: String
        get() = "build/intermediates/merged_java_res/$buildVariants"


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


        allModule.forEach { module ->
            val libraryMergeDirectory = module.getFileManager()
                .resolveFile(libraryMergeAssetsResourceDirectory, module)

            getTaskInput()
                .addInputDirectory(libraryMergeDirectory)

        }


        getTaskInput().transformDirectoryToFile { it.isFile }

        return super.prepare()

    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val outputDirectory = applicationModule
            .getFileManager()
            .resolveFile(mergeAssetsOutputDirectory, applicationModule)

        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach {
            val target = File(
                outputDirectory,
                it.toFile().path.substring(it.getSourceDirectory().length + 1)
            )

            it.toFile().copyTo(target)

            getTaskInput()
                .bindOutputFile(it, target)

        }

        getTaskInput()
            .snapshot()



    }
}