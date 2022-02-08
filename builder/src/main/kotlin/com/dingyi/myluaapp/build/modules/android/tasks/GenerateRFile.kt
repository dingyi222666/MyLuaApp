package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser
import com.dingyi.myluaapp.build.modules.android.symbol.SymbolLoader
import com.dingyi.myluaapp.build.modules.android.symbol.SymbolWriter
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.println
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

        val allModule = applicationModule
            .getProject()
            .getModules()
            .filterNot { it == applicationModule }
            .filter {
                val file = it.getFileManager()
                    .resolveFile(
                        "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                        it
                    )

                file.exists() && file.length() > 0
            }

        println(allModule)

        //Check Module

        val incrementalModule = allModule
            .filterNot {
                val file = it.getFileManager()
                    .resolveFile(
                        "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                        it
                    )

                it.getFileManager()
                    .equalsSnapshot(file)
            }

        val allLibraries = applicationModule
            .getProject()
            .getAllDependencies()
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
                    }", "compile_r.txt"
                )
            }
            .filter {
                it.exists() && it.length() > 0
            }

        val incrementalLibraries =
            allLibraries
                .filterNot {
                    applicationModule
                        .getFileManager()
                        .equalsSnapshot(it)
                }

        if (allLibraries.isEmpty() && allModule.isEmpty()) {
            return Task.State.SKIPPED
        }

        incrementalModule.forEach {
            generateModules.add(it)
        }

        incrementalLibraries.forEach {

            generateLibraries.add(it.parentFile)
        }

        println("Librarties R File",allLibraries)

        return when {
            incrementalModule.isEmpty() && incrementalLibraries.isEmpty() -> Task.State.`UP-TO-DATE`
            allLibraries.size > incrementalLibraries.size ||
                    allModule.size > incrementalModule.size -> Task.State.INCREMENT
            else -> Task.State.DEFAULT
        }
    }

    override suspend fun run() {

        generateModules.forEach {
            generateModuleRFile(it)
        }

        generateLibraries.forEach {
            generateLibrariesRFile(it)
        }
    }

    private suspend fun generateLibrariesRFile(file: File) = withContext(Dispatchers.IO) {
        val symbolLoader =
            SymbolLoader.load(
                File(file, "compile_r.txt")
            )

        symbolLoader.merge(
            applicationModule.getFileManager().resolveFile(
                "build/intermediates/symbol_list/R.txt", applicationModule
            )
        )

        val manifestInfo = AndroidManifestSimpleParser()
            .parse(
                File(file, "AndroidManifest.xml").path
            )


        SymbolWriter(
            manifestInfo.packageId ?: ""
        ).write(
            symbolLoader,
            applicationModule
                .getFileManager()
                .resolveFile(outputDirectory, applicationModule)
        )

    }

    private suspend fun generateModuleRFile(module: Module) = withContext(Dispatchers.IO) {
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


        generateModules
            .forEach {
                val file = it.getFileManager()
                    .resolveFile(
                        "build/intermediates/compile_local_symbol_list/${buildVariants}/R.txt",
                        it
                    )

                it.getFileManager()
                    .snapshot(file)
            }

        generateLibraries
            .filter {
                applicationModule
                    .getFileManager()
                    .snapshot(it)
            }

    }

}