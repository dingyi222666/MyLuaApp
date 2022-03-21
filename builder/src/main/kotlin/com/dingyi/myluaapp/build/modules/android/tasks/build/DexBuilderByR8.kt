package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.android.tools.r8.*
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.endsWith
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.ktx.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaNumber
import org.luaj.vm2.LuaTable
import java.io.File
import java.util.*
import kotlin.io.path.Path

class DexBuilderByR8(private val module: Module) : DefaultTask(module) {

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
            }ByR8"
        }
        return javaClass.simpleName
    }

    private val classCompileDirectory: String
        get() = "build/intermediates/javac/$buildVariants/classes"

    private val outputDirectory: String
        get() = "build/intermediates/merge_project_dex/$buildVariants"

    private val mappingOutputDirectory: String
        get() = "build/outputs/mapping/$buildVariants/mapping.txt"


    private val proguardFiles = mutableListOf<File>()

    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants


        module
            .getProject()
            .getAllModule()
            .flatMap { subModule ->

                val jarFiles = subModule
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
                                    if (it.name == "proguard.txt") {
                                        proguardFiles.add(it)
                                    }
                                    true
                                }
                                .toList()
                        } else {
                            listOf(file)
                        }
                    }.filter {
                        it.name.endsWith("jar") && it.exists()
                    }


                val classFiles = subModule.getFileManager()
                    .resolveFile(classCompileDirectory, subModule)
                    .walkBottomUp()
                    .filter { it.isFile && it.name.endsWith("class") }

                val subModuleProguardFiles = subModule
                    .getMainBuilderScript()
                    .get(
                        "android.buildTypes.$buildVariants.proguardFiles"
                    ).let {
                        mutableListOf<String>().apply {
                            if (it is LuaTable) {
                                for (key in it.keys()) {
                                    add(it[key].tojstring())
                                }
                            }
                        }
                    }.map {
                        val file = it.toFile()
                        if (file.exists()) {
                            file
                        } else {
                            subModule.getFileManager()
                                .resolveFile(it, subModule).apply {
                                    if (!isFile) {
                                        throw CompileError("Unable to indexed proguard file:$it")
                                    }
                                }
                        }
                    }

                val aaptOut = subModule
                    .getFileManager()
                    .resolveFile("build/generated/proguard/$buildVariants/out", subModule)

                if (aaptOut.isDirectory) {
                    aaptOut.walkBottomUp()
                        .filter { it.isFile }
                        .forEach { proguardFiles.add(it) }
                }

                proguardFiles.addAll(subModuleProguardFiles)


                jarFiles.plus(classFiles)

            }
            .forEach {
                getTaskInput()
                    .addInputFile(it)
            }

        super.prepare()


        //if use r8,the INCREMENT not support it
        return Task.State.DEFAULT
    }


    override suspend fun run(): Unit = withContext(Dispatchers.IO) {

        val allInputFile = getTaskInput()
            .getAllInputFile()

        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)

        if (outputDirectory.isFile) {
            outputDirectory.deleteRecursively()
        }

        outputDirectory.mkdirs()

        val minSdk = getMinSdk()

        val command = R8Command
            .builder()
            .apply {
                if (minSdk < 21) {
                    addMainDexRulesFiles(Path(Paths.buildPath, "proguard/mainDexClasses.rules"))
                }
            }
            .setProguardMapOutputPath(
                module
                    .getFileManager()
                    .resolveFile(mappingOutputDirectory, module)
                    .apply {
                        parentFile?.mkdirs()
                        createNewFile()
                    }
                    .toPath()
            )
            .addProguardConfigurationFiles(proguardFiles
                .map { it.toPath() })
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )

            .addProgramFiles(allInputFile
                .map { it.toFile().toPath() })
            .setMinApiLevel(minSdk)
            .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
            .setOutput(
                outputDirectory.toPath(), OutputMode.DexIndexed
            )
            .build()

        R8.run(command)


        proguardFiles.clear()


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