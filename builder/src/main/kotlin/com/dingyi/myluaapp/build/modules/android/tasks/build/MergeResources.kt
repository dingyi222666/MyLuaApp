package com.dingyi.myluaapp.build.modules.android.tasks.build


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.InputFile
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MergeResources(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private lateinit var buildConfig: BuildConfig

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Merge${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Resources"
        }
        return javaClass.simpleName
    }


    private lateinit var compileXmlList: List<InputFile>

    override suspend fun prepare(): Task.State {

        buildConfig = module.getCache().getCache("${module.name}_build_config")

        buildVariants = buildConfig.buildVariants


        getTaskInput().let { taskInput ->
            taskInput
                .addInputDirectory(
                    module
                        .getFileManager()
                        .resolveFile("src/main/res", module)
                )
            taskInput
                .addOutputDirectory(
                    module
                        .getFileManager()
                        .resolveFile(
                            "build/intermediates/merged_res/${buildVariants}",
                            module
                        )
                )
            taskInput
                .addOutputDirectory(
                    module
                        .getFileManager()
                        .resolveFile(
                            "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                            module
                        )
                )

            taskInput
                .transformDirectoryToFile { it.isFile}
        }


        return super.prepare()
    }

    override suspend fun run() {

        compileXmlList = getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }

        val aapt2Compiler = AAPT2Compiler(module.getLogger())

        val outputDirectory = getTaskInput().getOutputDirectory()[0]

        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }


        val symbolOutputFile = getTaskInput().getOutputDirectory()[1]

        if (!symbolOutputFile.exists()) {
            symbolOutputFile.parentFile?.mkdirs()
            symbolOutputFile.createNewFile()
        }

        aapt2Compiler.compile(
            compileXmlList.map { it.toFile().path },
            outputDirectory.path,
            arrayOf(
                "--legacy", "--output-text-symbols",
                symbolOutputFile.path
            )
        )

        aapt2Compiler.close()


        withContext(Dispatchers.IO) {

            val allFileList = outputDirectory.walkBottomUp()
                .filter { it.isFile }
                .toList()

            compileXmlList.forEach { inputFile ->
                val name = inputFile.toFile().parentFile.name + "_" + inputFile.toFile()
                    .name.substring(0, inputFile.toFile().name.indexOf("."))

                allFileList.find {
                    it.name.substring(0, name.length) == name
                }?.let { out ->
                    getTaskInput().bindOutputFile(inputFile, out)
                }

            }

            getTaskInput().snapshot()

        }

    }


}