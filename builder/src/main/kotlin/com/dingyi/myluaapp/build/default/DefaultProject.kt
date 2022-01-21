package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.runner.Runner
import com.dingyi.myluaapp.build.api.script.Script
import com.dingyi.myluaapp.build.api.task.Task
import java.io.File

open class DefaultProject(private val path:String) :Project {

    override val name: String
        get() = getDefaultName()


    private val defaultSettingsScript = DefaultScript(
        File(path,"settings.gradle.lua").path
    )

    private val defaultMainBuilderScript = DefaultScript(
        File(path,"builder.gradle.lua").path
    )

    private val allScript = mutableListOf(defaultMainBuilderScript,defaultSettingsScript)

    private val builder = DefaultBuilder(getMainModule())

    private fun getDefaultName():String {
        return defaultSettingsScript.get("rootProject.name").toString()
    }

    override fun getTasks(type:String): List<Task> {
        return when (type) {
            "clean" -> builder.clean()
            "sync" -> builder.sync()
            "build" -> builder.getTasks()
            else -> builder.getTasks()
        }
    }

    override fun getModules(): List<Module> {
        TODO("Not yet implemented")
    }

    override fun getFileManager(): FileManager {
        TODO("Not yet implemented")
    }

    override fun getRunner(): Runner {
        TODO("Not yet implemented")
    }

    override fun init() {
        defaultSettingsScript.run()

        //TODO:find all modules

        defaultMainBuilderScript.run()


        indexAllModule()

    }

    override fun createModulesWeight(): Map<Int, List<Module>> {
        TODO()
    }

    override fun getBuilder(): Builder {
        return  builder
    }

    override fun getMainModule(): Module {
        return DefaultModule(this,"")
    }

    override fun indexAllModule() {
        TODO("Not yet implemented")
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
}