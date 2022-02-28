package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.Paths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaNumber
import java.io.File
import java.util.*

class DexBuilder(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "DexBuilder${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }"
        }
        return javaClass.simpleName
    }

    private val classToDexDirectory: String
        get() = "build/intermediates/merge_ext_dex/$buildVariants"

    private val jarToDexDirectory: String
        get() = "build/intermediates/library_dex_archive/$buildVariants"

    private val outputDirectory: String
        get() = "build/intermediates/merge_project_dex/$buildVariants"


    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants

        module
            .getProject()
            .getAllModule()
            .flatMap {
                listOf(
                    it.getFileManager()
                        .resolveFile(classToDexDirectory, it),
                    it.getFileManager()
                        .resolveFile(jarToDexDirectory, it)
                )
            }.flatMap { file ->
                file.walkBottomUp()
                    .filter { it.isFile && it.name.endsWith("dex") }
            }.forEach {
                getTaskInput().addInputFile(it)
            }

        return super.prepare()

    }

    private fun getMustMergeDex(): Boolean {
        //TODO get for script
        return false
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        if (buildVariants == "debug") {
            if (getMustMergeDex()) {
                doRelease()
            } else {
                doDebug()
            }
        } else {
            doRelease()
        }

        getTaskInput().snapshot()

    }

    private fun doDebug() {
        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)
            .apply {
                mkdirs()
            }

        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEachIndexed { index, inputFile ->

            val file = inputFile.toFile()

            val targetFile = getDebugMergeDexFile(outputDirectory, index)

            if (targetFile.isFile) {
                if (targetFile.getSHA256() != file.getSHA256()) {
                    targetFile.delete()
                    file.copyTo(
                        getDebugMergeDexFile(outputDirectory, index)
                    )
                }
            } else {
                file.copyTo(
                    getDebugMergeDexFile(outputDirectory, index)
                )
            }
            getTaskInput()
                .bindOutputFile(inputFile, targetFile)
        }


    }

    private fun getDebugMergeDexFile(directory: File, index: Int): File {
        val builder = StringBuilder()
        builder.append("classes")
        if (index > 0) {
            builder.append(index)
        }
        builder.append(".dex")
        return File(directory, builder.toString())
    }

    private fun doRelease() {

        val mergeDexFiles = getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }

        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)
            .apply {
                mkdirs()
            }

        val minSdk = getMinSdk()


        val command = D8Command
            .builder()
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )
            .apply {
                if (minSdk < 21) {
                    addMainDexRulesFiles(
                        File(
                            Paths.buildPath,
                            "proguard/mainDexClasses.rules"
                        ).toPath()
                    )
                }
            }
            .addProgramFiles(mergeDexFiles.map { it.toFile().toPath() })
            .setMinApiLevel(getMinSdk())
            .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
            .setOutput(
                outputDirectory.toPath(), OutputMode.DexIndexed
            )
            .build()

        D8.run(command)

        val allOutputFile = outputDirectory
            .walkBottomUp()
            .filter { it.isFile && it.name.endsWith("dex") }
            .toList()

        mergeDexFiles.forEach {
            getTaskInput()
                .bindOutputFiles(it, allOutputFile)
        }


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