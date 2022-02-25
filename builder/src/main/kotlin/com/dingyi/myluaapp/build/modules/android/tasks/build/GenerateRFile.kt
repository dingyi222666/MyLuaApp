package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.InputFile
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser
import com.dingyi.myluaapp.build.modules.android.symbol.SymbolLoader
import com.dingyi.myluaapp.build.modules.android.symbol.SymbolWriter
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class GenerateRFile(private val applicationModule: Module) : DefaultTask(applicationModule) {

    private lateinit var buildVariants: String

    private val outputDirectory: String
        get() = "build/generated/ap_generated_source/$buildVariants/out"


    override val name: String
        get() = "GenerateRFile"

    private val generateModules = mutableListOf<Module>()

    private val generateLibraries = mutableListOf<File>()

    override suspend fun prepare(): Task.State {

        buildVariants =
            applicationModule.getCache()
                .getCache<BuildConfig>("${applicationModule.name}_build_config")
                .buildVariants

        applicationModule
            .getProject()
            .getAllModule()
            .filterNot { it == applicationModule }
            .map {
                it.getFileManager()
                    .resolveFile(
                        "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                        it
                    )
            }.forEach {
                if (it.isFile) {
                    getTaskInput().addInputFile(it)
                }
            }


        //Check Module

        applicationModule
            .getProject()
            .getAllDependency()
            .filter {
                it.type == "aar"
            }
            .flatMap {
                it.getDependenciesFile()
            }
            .toSet()
            .map {
                File(
                    "${Paths.extractAarDir}${File.separator}${
                        it.path.toMD5()
                    }", "R.txt"
                )
            }
            .filter {
                it.exists() && it.length() > 0
            }.forEach {
                getTaskInput().addInputFile(it)
            }



        return super.prepare()
    }

    override suspend fun run() {

        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach { inputFile ->
            if (inputFile.getSourceDirectory()
                    .contains("build/intermediates/compile_local_symbol_list")
            ) {
                val modulePath = inputFile.toFile()
                    .parentFile?.parentFile?.parentFile
                    ?.parentFile?.parentFile

                applicationModule.getProject()
                    .getAllModule()
                    .find { it.getPath() == modulePath?.path }
                    ?.let { it -> generateModuleRFile(inputFile, it) }

            } else {
                generateLibrariesRFile(inputFile)
            }
        }

        getTaskInput().snapshot()

    }

    private suspend fun generateLibrariesRFile(file: InputFile) = withContext(Dispatchers.IO) {

        val rFile = File(file.toFile().parentFile, "R.txt")

        val symbolLoader =
            SymbolLoader.load(
                rFile
            )

        val outputDirectory = applicationModule
            .getFileManager()
            .resolveFile(outputDirectory, applicationModule)

        symbolLoader.merge(
            applicationModule.getFileManager().resolveFile(
                "build/intermediates/symbol_list/R.txt", applicationModule
            )
        )

        val manifestInfo = AndroidManifestSimpleParser()
            .parse(
                File(file.toFile().parentFile, "AndroidManifest.xml").path
            )


        SymbolWriter(
            manifestInfo.packageId ?: ""
        ).write(
            symbolLoader,
            outputDirectory
        )

        getTaskInput()
            .bindOutputFile(
                file,
                File(outputDirectory, manifestInfo.packageId?.replace(".", "/") + "R.java")
            )


    }

    private suspend fun generateModuleRFile(file: InputFile, module: Module) =
        withContext(Dispatchers.IO) {
            val symbolLoader =
                SymbolLoader.load(
                    module.getFileManager()
                        .resolveFile(
                            "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                            module
                        )
                )

            symbolLoader.merge(
                applicationModule.getFileManager().resolveFile(
                    "build/intermediates/symbol_list/R.txt", applicationModule
                )
            )

            val manifestInfo = AndroidManifestSimpleParser()
                .parse(
                    module.getFileManager()
                        .resolveFile(
                            "build/intermediates/merged_manifest/AndroidManifest.xml",
                            module
                        ).path
                )

            SymbolWriter(
                manifestInfo.packageId ?: ""
            ).write(
                symbolLoader,
                module
                    .getFileManager()
                    .resolveFile(outputDirectory, module)
            )

            getTaskInput()
                .bindOutputFile(
                    file,
                    File(outputDirectory, manifestInfo.packageId?.replace(".", "/") + "R.java")
                )

        }

}