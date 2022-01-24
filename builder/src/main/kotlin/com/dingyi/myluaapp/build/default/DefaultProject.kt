package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.runner.Runner
import com.dingyi.myluaapp.build.api.script.Script
import com.dingyi.myluaapp.build.api.task.Task
import com.dingyi.myluaapp.build.dependency.ProjectDependency
import com.dingyi.myluaapp.build.script.DefaultScript
import com.dingyi.myluaapp.common.kts.toFile
import org.luaj.vm2.LuaTable
import java.io.File

open class DefaultProject(
    private val path: String,
    private val mainBuilder: MainBuilder
) : Project {

    override val name: String
        get() = getDefaultName()


    private val defaultSettingsScript = DefaultScript(
        File(path, "settings.gradle.lua").path
    )

    private val defaultMainBuilderScript = DefaultScript(
        File(path, "build.gradle.lua").path
    )

    private val defaultFileManager = DefaultFileManager(path)

    private val allScript = mutableListOf(defaultMainBuilderScript, defaultSettingsScript)

    private val builder = DefaultProjectBuilder(this)

    private fun getDefaultName(): String {
        return defaultSettingsScript.get("rootProject.name").toString()
    }

    private val defaultRunner = DefaultRunner(this)

    private var mainModule: Module = DefaultModule(this, "")

    private val allModules = mutableListOf<Module>()

    override fun getTasks(type: String): List<Task> {
        return when (type) {
            "clean" -> builder.clean()
            "sync" -> builder.sync()
            "build" -> builder.getTasks()
            else -> builder.getTasks()
        }
    }

    override fun getModules(): List<Module> {
        return allModules
    }

    override fun getFileManager(): FileManager {
        return defaultFileManager
    }

    override fun getRunner(): Runner {
        return defaultRunner
    }

    override fun init() {

        indexAllScript()

        defaultSettingsScript.run()

        indexAllModule()

        defaultMainBuilderScript.run()


    }

    private fun indexAllScript() {
        path.toFile().listFiles()?.forEach { childDir ->
            if (childDir.isDirectory) {
                childDir.listFiles()?.forEach {
                    if (it.isFile && it.name.contains(".gradle.lua")) {
                        allScript.add(DefaultScript(it.path).apply {
                            run()
                        })
                    }
                }
            }
        }
    }

    override fun createModulesWeight(): Map<Int, List<Module>> {
        val result = mutableMapOf<Int, List<Module>>()
        val tmpMap = mutableMapOf<Module, Int>()
        allModules.forEach { module ->
            val dependencies = module.getDependencies()
            dependencies.forEach {
                if (it is ProjectDependency) {
                    checkEachOther(it, module)
                    val targetModule = it.getModule(this)
                    val num = tmpMap.getOrDefault(targetModule) { 0 }
                    tmpMap[targetModule] = num as Int
                }
            }
            tmpMap.getOrDefault(module) { 0 }
        }
        tmpMap.forEach { (a, b) ->
            if (a != getMainModule()) {
                (result.getOrDefault(b) {
                    mutableListOf<Module>()
                } as MutableList<Module>).add(a)
            }
        }
        result[-1] = listOf(mainModule)
        return result
    }

    private fun checkEachOther(projectDependency: ProjectDependency, module: Module) {
        for (dependency in projectDependency.getModule(this).getDependencies()) {
            if (dependency is ProjectDependency) {
                if (dependency.getModule(this) == module) {
                    throw CompileError("Do not interdepend module")
                }
            }
        }
    }

    override fun getBuilder(): Builder {
        return builder
    }

    override fun getMainModule(): Module {
        return mainModule
    }

    override fun indexAllModule() {
        println(defaultSettingsScript.get("includes"))
        val table = defaultSettingsScript.get("includes") as LuaTable
        table.keys().forEach {
            val value = table[it].tojstring().toString()
            indexModule(value)
        }
        if (allModules.size == 1) {
            mainModule = allModules[0]
        }
        allModules.forEach {
            it.init()
        }
    }

    open fun indexModule(value: String) {
        val dir = File(path, value)
        if (dir.isDirectory) {

            val module = getMainBuilder().getServiceRepository().onCreateModule(dir.path, this)
            if (module != null) {
                if (module.name == "src" || module.name == "app") {
                    mainModule = module
                }
                allModules.add(module)
            }

        }
    }

    override fun getMainBuilder(): MainBuilder {
        return mainBuilder
    }

    override fun getLogger(): ILogger {
        return mainBuilder.getLogger()
    }


    override fun getModule(name: String): Module {
        return allModules.filter {
            it.name == name
        }.getOrNull(0) ?: mainModule
    }

    override fun getSettingsScript(): Script {
        return defaultSettingsScript
    }

    override fun getMainBuilderScript(): Script {
        return defaultMainBuilderScript
    }

    override fun getAllScript(): List<Script> {
        return allScript
    }

    override fun toString(): String {
        return "DefaultProject(\npath='$path', \nmainBuilder=$mainBuilder, \nname='$name', \ndefaultSettingsScript=$defaultSettingsScript, \ndefaultMainBuilderScript=$defaultMainBuilderScript, \ndefaultFileManager=$defaultFileManager, \nallScript=$allScript, \nbuilder=$builder, \ndefaultRunner=$defaultRunner, \nmainModule=$mainModule, \nallModules=$allModules\n)"
    }
}