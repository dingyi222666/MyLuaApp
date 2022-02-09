package com.dingyi.myluaapp.build.modules.android.tasks

import android.provider.ContactsContract
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

    private fun doDebug() {
        val outputDirectory = module.getFileManager()
            .resolveFile(outputDirectory, module)
            .apply {
                mkdirs()
            }

        mergeDexFiles.forEachIndexed { index, file ->

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
        val command = D8Command
            .builder()
            .addLibraryFiles(
                File(Paths.buildPath, "jar/core-lambda-stubs.jar").toPath(),
                File(Paths.buildPath, "jar/android.jar").toPath()
            )
            .addMainDexRulesFiles(File(Paths.buildPath, "proguard/mainDexClasses.rules").toPath())
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