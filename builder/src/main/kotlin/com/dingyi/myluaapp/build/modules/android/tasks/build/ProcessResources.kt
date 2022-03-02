package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.ktx.toMD5
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


    override suspend fun prepare(): Task.State {
        buildConfig = module.getCache().getCache("${module.name}_build_config")

        buildVariants = buildConfig.buildVariants

        //Check Main Resources

        module.getFileManager()
            .resolveFile(mainModuleFlatOutputDirectory, module)
            .walkBottomUp()
            .filter { it.isFile and it.name.endsWith("flat") }
            .forEach {
                getTaskInput().addInputFile(it)
            }


        //Check Other Module Resources

        module.getProject()
            .getAllModule()
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
            }.flatMap { pair ->
                if (pair.first.isDirectory) {
                    pair.first.walkBottomUp()
                        .filter {
                            it.isFile and it.name.endsWith("flat")
                        }.map {
                            it to pair.second
                        }.toList()
                } else listOf()
            }.forEach {
                getTaskInput().addInputFile(it.first)
            }


        //Check Libraries

        module
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
                    }".toFile(), "compile_res.zip"
                )
            }
            .forEach {
                if (it.exists()) {
                    getTaskInput().addInputFile(it)
                }
            }

        return super.prepare()
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

        val publicFile = module.getFileManager().resolveFile(
            "build/intermediates/symbol_list/public.txt", module
        )

        if (publicFile.exists()) {
            compiledCommand.add("--stable-ids")
            compiledCommand.add(publicFile.path)
        } else {
            compiledCommand.add("--emit-ids")
            compiledCommand.add(publicFile.path)
        }


        val allCompileFile = getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.groupBy { it.toFile().name == "compile_res.zip" }

        allCompileFile[true]?.forEach {
            compiledCommand.add("-R")
            compiledCommand.add(it.toFile().path)
        }


        allCompileFile[false]?.forEach {
            compiledCommand.add(it.toFile().path)
        }


        val outputFile = module.getFileManager()
            .resolveFile(outputFile, module).apply {
                parentFile?.mkdirs()
            }

        compiler
            .link(
                linkMainFlatList,
                outputFile.path,
                compiledCommand.toTypedArray()
            )

        allCompileFile.values.flatten()
            .forEach {
                getTaskInput()
                    .bindOutputFile(it,outputFile)
            }

        getTaskInput().snapshot()


    }
}