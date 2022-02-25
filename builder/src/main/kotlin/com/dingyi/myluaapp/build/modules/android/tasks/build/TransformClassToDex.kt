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
import com.dingyi.myluaapp.common.kts.endsWith
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaNumber
import java.io.File
import java.nio.file.Path
import java.util.*

class TransformClassToDex(private val module: Module) : DefaultTask(module) {

    private val classCompileDirectory: String
        get() = "build/intermediates/javac/$buildVariants/classes"

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Transform${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }ClassToDex"
        }
        return javaClass.simpleName
    }


    private val outputDirectory: String
        get() = "build/intermediates/dex_archive/$buildVariants"


    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants


        getTaskInput().addInputDirectory(
            module.getFileManager()
                .resolveFile(classCompileDirectory, module)
        )

        getTaskInput().transformDirectoryToFile { it.isFile && it.name.endsWith("class") }


        return super.prepare()

    }

    private fun getLibraryFiles(): List<Path> {
        return module
            .getDependencies()
            .flatMap {
                it.getDependenciesFile()
            }.flatMap { file ->
                if (file.name.endsWith("aar")) {
                    val extractFile = File(
                        "${Paths.extractAarDir}${File.separator}${
                            file.path.toMD5()
                        }"
                    )
                    extractFile.walkBottomUp()
                        .filter {
                            it.isFile && it.name.endsWith("jar")
                        }
                        .toList()
                } else {
                    listOf(file)
                }
            }.filter {
                it.name.endsWith("jar") && it.exists()
            }.toMutableList()
            .map { it.toPath() }
    }

    override suspend fun run(): Unit = withContext(Dispatchers.IO) {

        val transformDexFiles = getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }

        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)
            .apply {
                mkdirs()
            }

        val command = D8Command
            .builder()
            .addClasspathFiles(getLibraryFiles())
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )
            .addProgramFiles(transformDexFiles.map { it.toFile().toPath() })
            .setIntermediate(true)
            .setMinApiLevel(getMinSdk())
            .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
            .setOutput(
                outputDirectory.toPath(), OutputMode.DexFilePerClassFile
            )
            .build()

        D8.run(command)

        transformDexFiles.forEach {
            val name = it.toFile().path
                .substring(it.getSourceDirectory().length + 1)
                .replace(".class", ".dex")

            getTaskInput()
                .bindOutputFile(it, File(outputDirectory, name))
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

    private fun getClassTransformDexPath(classPath: File): File {
        val inputDirectory = module
            .getFileManager()
            .resolveFile(classCompileDirectory, module)

        return File(
            module
                .getFileManager()
                .resolveFile(outputDirectory, module),
            classPath.path.substring(inputDirectory.path.length + 1)
        )

    }


}