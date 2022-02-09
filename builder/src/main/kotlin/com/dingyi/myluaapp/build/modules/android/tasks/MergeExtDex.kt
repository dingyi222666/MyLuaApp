package com.dingyi.myluaapp.build.modules.android.tasks

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


    private val jarToDexDirectory: String
        get() = "build/intermediates/library_dex_archive/$buildVariants"

    private val outputDirectory: String
        get() = "build/intermediates/merge_ext_dex/$buildVariants"


    private val mergeDexFiles = mutableListOf<File>()

    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants

        val allMergeDexFiles = module
            .getProject()
            .getAllModule()
            .flatMap {
                listOf(
                    it.getFileManager()
                        .resolveFile(classToDexDirectory,it),
                    it.getFileManager()
                        .resolveFile(jarToDexDirectory,it)
                )
            }.flatMap { file ->
                file.walkBottomUp()
                    .filter { it.isFile  && it.name.endsWith("dex") }
            }

        if (allMergeDexFiles.isEmpty()) {
            return Task.State.SKIPPED
        }

        val incrementalMergeDexFiles = allMergeDexFiles.filterNot {
             module.getFileManager().equalsSnapshot(it)
        }

        val outputDirectory = module.getFileManager().resolveFile(outputDirectory,module)

        val incrementalOutputDirectory =
            outputDirectory.walkBottomUp()
                .filter { it.isFile && it.name.endsWith("dex") }
                .map { module.getFileManager().equalsSnapshot(it) }
                .toList()


        if (incrementalOutputDirectory.isEmpty() || incrementalOutputDirectory.contains(false)) {
            mergeDexFiles.addAll(allMergeDexFiles)
            return Task.State.DEFAULT
        }

        mergeDexFiles.addAll(incrementalMergeDexFiles)

        return when {
            incrementalMergeDexFiles.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalMergeDexFiles.size < allMergeDexFiles.size -> Task.State.INCREMENT
            incrementalMergeDexFiles.size == allMergeDexFiles.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }

    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val command = D8Command
            .builder()
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )
            .addMainDexRulesFiles(File(Paths.buildPath,"proguard/mainDexClasses.rules").toPath())
            .addProgramFiles(mergeDexFiles.map { it.toPath() })
            .setMinApiLevel(getMinSdk())
            .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
            .setOutput(
                module.getFileManager()
                    .resolveFile(outputDirectory, module)
                    .apply {
                        mkdirs()
                    }
                    .toPath(), OutputMode.DexIndexed
            )
            .build()

        D8.run(command)

        mergeDexFiles.forEach {
            module
                .getFileManager()
                .snapshot(it)
        }

        module.getFileManager()
            .resolveFile(outputDirectory, module)
            .walkBottomUp()
            .filter { it.isFile && it.name.endsWith("dex") }
            .forEach {
                module
                    .getFileManager()
                    .snapshot(it)
            }

    }


    private fun getMinSdk():Int {
        val minSdkString = module
            .getMainBuilderScript()
            .get("android.defaultConfig.minSdkVersion")

        if (minSdkString is LuaNumber) {
            return minSdkString.toint()
        }

        return 21
    }
}