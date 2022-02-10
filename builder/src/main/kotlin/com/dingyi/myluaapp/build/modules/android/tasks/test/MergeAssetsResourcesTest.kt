package com.dingyi.myluaapp.build.modules.android.tasks.test

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.InputFile
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.println
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MergeAssetsResourcesTest(
    private val module: Module
) : DefaultTask(module) {

    override val name: String
        get() = "MergeAssetsResources"

    private lateinit var buildVariants: String


    private val mergeDirectory = arrayOf(
        "src/main/assets", //main sources
        "assets" // aar
    )


    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants



        getTaskInput()
            .addInputDirectory(
                module
                    .getFileManager()
                    .resolveFile(mergeDirectory[0], module)
            )

        module
            .getDependencies()
            .filter { it.type == "aar" }
            .flatMap {
                it.getDependenciesFile()
            }
            .map { file ->
                File(
                    "${Paths.extractAarDir}${File.separator}${
                        file.path.toMD5()
                    }", "assets"
                )
            }.forEach {
                if (it.isDirectory) {
                    getTaskInput()
                        .addInputDirectory(it)
                }
            }


        getTaskInput().transformDirectoryToFile { it.isFile }


        return super.prepare()
    }


    override suspend fun run() {


        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach {
            val file = it.toFile()
            val target = getMergeAssetsPath(it)

            if (target.exists()) {
                target.delete()
            }

            file.copyTo(target)

            getTaskInput().bindOutputFile(it, target)
        }

        getTaskInput().snapshot()


    }


    private fun getMergeAssetsPath(file: InputFile): File {
        return module
            .getFileManager()
            .resolveFile("build/intermediates/library_assets/$buildVariants", module)
            .let {
                File(it, file.toFile().path.substring(file.getSourceDirectory().length + 1))
            }

    }


}