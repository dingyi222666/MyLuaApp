package com.dingyi.myluaapp.build.modules.android.tasks

import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.dependency.ProjectDependency
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

class TransformJarToDex(private val module: Module) : DefaultTask(module) {
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
            }JarToDex"
        }
        return javaClass.simpleName
    }

    private val transformJarToDexFiles = mutableListOf<File>()


    private val outputDirectory: String
        get() = "build/intermediates/library_dex_archive/$buildVariants"

    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants


        val allTransformJarToDexList = module
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
            }

        if (allTransformJarToDexList.isEmpty()) {
            return Task.State.SKIPPED
        }

        val incrementalTransformJarToDexList =
            allTransformJarToDexList.filterNot {
                module.getFileManager()
                    .equalsSnapshot(it) and module
                    .getFileManager()
                    .equalsSnapshot(getJarTransformDexPath(it))
            }

        transformJarToDexFiles.addAll(incrementalTransformJarToDexList)

        return when {
            incrementalTransformJarToDexList.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalTransformJarToDexList.size < allTransformJarToDexList.size -> Task.State.INCREMENT
            incrementalTransformJarToDexList.size == allTransformJarToDexList.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }




    override suspend fun run() = withContext(Dispatchers.IO) {
        transformJarToDexFiles.forEach { jarFile ->
            val targetDirectory = getJarTransformDexDirectory(jarFile)

            if (targetDirectory.exists()) {
                targetDirectory.deleteRecursively()
            }

            targetDirectory.mkdirs()

            val command = D8Command.builder()
                //.addClasspathFiles(getLibraryFiles())
                .addLibraryFiles(
                    File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                    File(Paths.buildPath, "jar/android.jar").toPath()
                )
                .addProgramFiles(jarFile.toPath())
                .setIntermediate(true)
                .setMinApiLevel(getMinSdk())
                .setMode(if (buildVariants == "debug") CompilationMode.DEBUG else CompilationMode.RELEASE)
                .setOutput(
                    targetDirectory.toPath(), OutputMode.DexIndexed
                )
                .build()


            D8.run(command)
        }

        transformJarToDexFiles.forEach {
            module.getFileManager()
                .snapshot(it)
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


    private fun getJarTransformDexDirectory(jarFile: File): File {
        return File(module
            .getFileManager().resolveFile(outputDirectory,module).path + "/" + jarFile.path.toMD5())
    }

    private fun getJarTransformDexPath(jarFile: File): File {
        return File(getJarTransformDexDirectory(jarFile), "classes.dex")
    }
}