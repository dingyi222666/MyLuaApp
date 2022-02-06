package com.dingyi.myluaapp.build.modules.android.tasks


import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class PackageResources(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private lateinit var buildConfig: BuildConfig

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Package${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Resources"
        }
        return javaClass.simpleName
    }

    private val outputDirectory: String
        get() = "build/intermediates/compiled_local_resources/${buildVariants}"

    private val symbolOutputFile: String
        get() = "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt"


    private val buildDirectory: String
        get() = "src/main/res"

    private lateinit var compileXmlList: List<String>

    override suspend fun prepare(): Task.State {
        buildConfig = module.getCache().getCache("${module.name}_build_config")

        buildVariants = buildConfig.buildVariants

        val buildDirectoryFile = module.getFileManager()
            .resolveFile(buildDirectory, module)

        if (!buildDirectoryFile.exists()) {
            return Task.State.SKIPPED
        }

        val allCompileXmlFileList = buildDirectoryFile.walkBottomUp()
            .filter {
                it.isFile and it.name.endsWith("xml")
            }
            .toList()

        if (allCompileXmlFileList.isEmpty()) {
            return Task.State.`NO-SOURCE`
        }

        val flatFiles = outputDirectory.toFile().walkBottomUp()
            .filter {
                it.isFile and it.name.endsWith("flat")
            }
            .filter {
                module.getFileManager()
                    .getSnapshotManager()
                    .equalsAndSnapshot(it)
                    .not()
            }.toList()

        val incrementalCompileXmlFileList = if (flatFiles.isEmpty()) {
            allCompileXmlFileList.filter {
                module.getFileManager()
                    .getSnapshotManager()
                    .equalsAndSnapshot(it)
                    .not()
            }.map { it.path }
        } else compileXmlList

        this.compileXmlList = incrementalCompileXmlFileList

        return when {
            incrementalCompileXmlFileList.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalCompileXmlFileList.size < allCompileXmlFileList.size -> Task.State.INCREMENT
            incrementalCompileXmlFileList.size == allCompileXmlFileList.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }

    }

    override suspend fun run() {

        val aapt2Compiler = AAPT2Compiler(module.getLogger())

        val outputDirectory = module.getFileManager().resolveFile(outputDirectory, module)

        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }


        val symbolOutputFile = module.getFileManager().resolveFile(
            symbolOutputFile, module
        )

        if (!symbolOutputFile.exists()) {
            symbolOutputFile.parentFile?.mkdirs()
            symbolOutputFile.createNewFile()
        }

        aapt2Compiler.compile(
            compileXmlList,
            outputDirectory.path,
            arrayOf(
                "--legacy", //"--output-text-symbols",
                //symbolOutputFile.path
            )
        )

        aapt2Compiler.close()

        if (outputDirectory.walkBottomUp().filter { it.isFile }.toList().isEmpty()) {
            throw CompileError("Unable to compile xml file! View log for get more details")
        }

        withContext(Dispatchers.IO) {
            outputDirectory.walkBottomUp()
                .filter { it.isFile }
                .forEach {
                    module.getFileManager().getSnapshotManager().snapshot(it)
                }
        }

    }



}