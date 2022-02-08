package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.R
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import java.io.File

class ProcessResources(private val module: Module) : DefaultTask(module) {

    private lateinit var buildVariants: String

    override val name: String
        get() = "ProcessResources"

    private val outputFile: String
        get() = "build/intermediates/processed_res/${buildVariants}/out/resources-${buildVariants}.ap_"


    private val mainManifestFile = "build/intermediates/merged_manifest/AndroidManifest.xml"

    private val mainModuleFlatOutputDirectory: String
        get() = "build/intermediates/merged_res/${buildVariants}"

    private val moduleFlatOutputDirectory = "build/intermediates/compiled_local_resources/%s"

    private lateinit var buildConfig: BuildConfig

    private val librariesCompileResources = mutableListOf<File>()

    private val mainCompileResources = mutableListOf<File>()

    private val moduleCompileResources = mutableListOf<File>()

    override suspend fun prepare(): Task.State {
        buildConfig = module.getCache().getCache("${module.name}_build_config")

        buildVariants = buildConfig.buildVariants

        //Check Main Resources

        val mainCompileResourceList = module.getFileManager()
            .resolveFile(mainModuleFlatOutputDirectory, module)
            .walkBottomUp()
            .filter { it.isFile and it.name.endsWith("flat") }
            .toList()


        //Check Other Module Resources

        val moduleResourceList = module.getProject()
            .getModules()
            .filter { it != module && it.type == "AndroidLibrary" }
            .map { libraryModule ->
                libraryModule
                    .getFileManager()
                    .resolveFile(
                        moduleFlatOutputDirectory.format(
                            libraryModule.getCache()
                                .getCache<BuildConfig>("${libraryModule.name}_build_config")
                                .buildVariants
                        ), libraryModule
                    ) to libraryModule
            }.filter {
                it.first.isDirectory
            }.flatMap { pair ->
                pair.first.walkBottomUp()
                    .filter {
                        it.isFile and it.name.endsWith("flat")
                    }.map {
                        it to pair.second
                    }
            }


        //Check Libraries

        val librariesResourceList = module
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
                    }".toFile(), "compile_res.zip"
                )
            }
            .filter {
                it.exists()
            }


        if (librariesResourceList.isEmpty() && moduleResourceList.isEmpty() &&
            mainCompileResourceList.isEmpty()
        ) {

            return Task.State.SKIPPED

        }


        val incrementalSize = librariesResourceList.filterNot {
            module
                .getFileManager()
                .equalsAndSnapshot(it)
        }.size + moduleResourceList.filterNot {
            it.second
                .getFileManager()
                .equalsAndSnapshot(it.first)
        }.size + mainCompileResourceList.filterNot {
            module
                .getFileManager()
                .equalsAndSnapshot(it)
        }.size

        if (incrementalSize == 0 && module
                .getFileManager()
                .equalsAndSnapshot(
                    module.getFileManager().resolveFile(outputFile, module)
                )
        ) {
            return Task.State.`UP-TO-DATE`
        }

        librariesResourceList.forEach {
            librariesCompileResources.add(it)
        }

        moduleResourceList.forEach {
            moduleCompileResources.add(it.first)
        }

        mainCompileResourceList.forEach {
            mainCompileResources.add(it)
        }

        return Task.State.DEFAULT
    }

    override suspend fun run() {

        val compiler = AAPT2Compiler(module.getLogger())


        val linkMainFlatList = mutableListOf<String>()

        val compiledCommand = mutableListOf(
            "-I",
            File(Paths.buildPath, "jar/android.jar").path,

            "--manifest",
            module.getFileManager().resolveFile(mainManifestFile, module).path,

            "--java",
            module.getFileManager().resolveFile(
                "build/generated/ap_generated_source/$buildVariants/out",
                module
            ).apply {
                mkdirs()
            }.path,

            "--proguard",
            module.getFileManager().resolveFile(
                "build/generated/proguard/$buildVariants/out/ap_generated_proguard.pro",
                module
            ).apply {
                parentFile?.mkdirs()
                createNewFile()
            }.path,

            "--output-text-symbols",
            module.getFileManager().resolveFile(
                "build/intermediates/symbol_list/R.txt", module
            ).apply {
                parentFile?.mkdirs()
                createNewFile()

            }.path,

            "--auto-add-overlay"
        )

        librariesCompileResources.forEach {
            compiledCommand.add("-R")
            compiledCommand.add(it.path)
        }


        mainCompileResources.forEach {
            linkMainFlatList.add(it.path)
        }

        moduleCompileResources.forEach {
            linkMainFlatList.add(it.path)
            //compiledCommand.add("-R")
            //compiledCommand.add(it.path)
        }

        compiler
            .link(
                linkMainFlatList,
                module.getFileManager()
                    .resolveFile(outputFile, module).apply {
                        parentFile?.mkdirs()
                    }
                    .path,
                compiledCommand.toTypedArray()
            )


    }
}