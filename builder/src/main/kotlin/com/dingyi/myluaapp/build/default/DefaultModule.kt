package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.script.Script
import java.io.File

open class DefaultModule(
    private val project: DefaultProject,
    private val path:String
):Module {
    override val type: String
        get() = "default"
    override val name: String
        get() = "DEFAULT_MODULE"

    private val defaultBuilder = DefaultBuilder(this)

    private val defaultSettingsScript = DefaultScript(
        File(path,"settings.gradle.lua").path
    )

    private val defaultMainBuilderScript = DefaultScript(
        File(path,"builder.gradle.lua").path
    )

    private val allScript = mutableListOf(defaultMainBuilderScript,defaultSettingsScript)


    private val dependencies = mutableListOf<Dependency>()

    override fun getBuilder(): Builder {
        return defaultBuilder
    }

    override fun init() {
        defaultMainBuilderScript.run()

        defaultSettingsScript.run()
    }

    override fun getDependencies(): List<Dependency> {
        return dependencies
    }

    override fun getFileManager(): FileManager {
        return project.getFileManager()
    }

    override fun getMainBuilderScript(): Script {
        return defaultMainBuilderScript
    }

    override fun getAllScript(): List<Script> {
        return allScript
    }
}