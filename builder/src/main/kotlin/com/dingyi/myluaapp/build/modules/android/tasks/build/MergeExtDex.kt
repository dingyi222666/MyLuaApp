package com.dingyi.myluaapp.build.modules.android.tasks.build


import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.Paths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaNumber
import java.io.File
import java.util.*

class MergeExtDex(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Merge${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }ExtDex"
        }
        return javaClass.simpleName
    }

    private val classToDexDirectory: String
        get() = "build/intermediates/dex_archive/$buildVariants"

    private val outputDirectory: String
        get() = "build/intermediates/merge_ext_dex/$buildVariants"


    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants

        module
            .getFileManager()
            .resolveFile(classToDexDirectory, module)
            .walkBottomUp()
            .filter { it.isFile && it.name.endsWith("dex") }
            .forEach {
                getTaskInput().addInputFile(it)
            }

        return super.prepare()

    }


    override suspend fun run() = withContext(Dispatchers.IO) {

        val mergeDexFiles = getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }

        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)
            .apply {
                mkdirs()
            }


      D8Command
            .builder()
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )
          .addProgramFiles(mergeDexFiles.map { it.toFile().toPath() })
          .setMinApiLevel(getMinSdk())
          .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
          .setOutput(
              outputDirectory.toPath(), OutputMode.DexIndexed
          )
          .build()
          .apply {
              D8.run(this)
          }

        val allOutputFile = outputDirectory
            .walkBottomUp()
            .filter { it.isFile && it.name.endsWith("dex") }
            .toList()

        mergeDexFiles
            .forEach {
                getTaskInput()
                    .bindOutputFiles(it, allOutputFile)
            }

        getTaskInput().snapshot()

    }


    private fun getMinSdk(): Int {
        val minSdkString = module
            .getMainBuilderScript()
            .get("android.defaultConfig.minSdkVersion")

        if (minSdkString is LuaNumber) {
            return minSdkString.toint()
        }

        return 21
    }
}