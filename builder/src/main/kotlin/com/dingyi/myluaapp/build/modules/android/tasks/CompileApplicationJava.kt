package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.dependency.ProjectDependency
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.public.compiler.JavaCompiler
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.endsWith
import com.dingyi.myluaapp.common.kts.toMD5
import org.luaj.vm2.LuaTable
import java.io.File
import java.util.*

class CompileApplicationJava(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Compile${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Java"
        }
        return javaClass.simpleName
    }

    private val compileJavaFiles = mutableListOf<File>()

    private val compileJavaDirectory = arrayOf("src/main/java", "build/generated")

    private val outputDirectory: String
        get() = "build/intermediates/javac/$buildVariants/classes"

    override suspend fun prepare(): Task.State {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants

        val allCompileJavaFile = compileJavaDirectory.map {
            module.getFileManager().resolveFile(it, module)
        }.flatMap { file ->
            file.walkBottomUp()
                .filter {
                    it.isFile && it.name.endsWith("java")
                }
        }.toMutableList()

        val moduleCompileJavaFile = module.getProject()
            .getAllModule()
            .filterNot { it == module }
            .flatMap { subModule ->
                subModule.getFileManager()
                    .resolveFile(compileJavaDirectory[1], subModule)
                    .walkBottomUp()
                    .filter {
                        it.name == "R.java" && it.path.contains("ap_generated_source")
                    }
            }

        allCompileJavaFile.addAll(moduleCompileJavaFile)

        if (allCompileJavaFile.isEmpty()) {
            return Task.State.`NO-SOURCE`
        }

        val incrementalCompileJavaFile =
            allCompileJavaFile.filterNot {
                module
                    .getFileManager()
                    .equalsSnapshot(it)
            }

        compileJavaFiles.addAll(incrementalCompileJavaFile)



        return when {
            incrementalCompileJavaFile.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalCompileJavaFile.size < allCompileJavaFile.size -> Task.State.INCREMENT
            incrementalCompileJavaFile.size == allCompileJavaFile.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }
    }

    override suspend fun run() {


        val javaCompiler = JavaCompiler(module.getLogger())

        val classPaths = module
            .getDependencies()
            .flatMap {
                if (it is ProjectDependency) {
                    getProjectDependency(it)
                } else {
                    it.getDependenciesFile()
                }
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
                it.name.endsWith("java", "jar") && it.exists()
            }

        val option = JavaCompiler.Option()

        // 不要用这个东西。。。 javac直接报错但是ecj正常输出 无语
        //option.addOption("v")

        val javaSourceTargetTable = module
            .getMainBuilderScript()
            .get("android.compileOptions")

        if (javaSourceTargetTable is LuaTable) {
            val sourceCompatibility = javaSourceTargetTable["sourceCompatibility"].tojstring()
            val targetCompatibility = javaSourceTargetTable["targetCompatibility"].tojstring()

            if (sourceCompatibility != "nil") {
                option.addOption("source", sourceCompatibility)
            }

            if (targetCompatibility != "nil") {
                option.addOption("target", targetCompatibility)
            }

        } else {
            option.addOption("source", "8")
            option.addOption("target", "8")
        }

        javaCompiler
            .compile(
                inputFiles = compileJavaFiles,
                classPaths = classPaths,
                option = option,
                outputDir = module
                    .getFileManager()
                    .resolveFile(outputDirectory, module)
                    .apply {
                        mkdirs()
                    },

                )

        compileJavaFiles.forEach {
            module.getFileManager().snapshot(it)
        }
    }

    private fun getProjectDependency(dependency: ProjectDependency): List<File> {
        val dependencyModule = dependency
            .getModule(module.getProject())
        return compileJavaDirectory.map {
            dependencyModule.getFileManager().resolveFile(it, module)
        }.flatMap { file ->
            file.walkBottomUp()
                .filter {
                    it.isFile && it.name.endsWith("java")
                }
        }
    }
}