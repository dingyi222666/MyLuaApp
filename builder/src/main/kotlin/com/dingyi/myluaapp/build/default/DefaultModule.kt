package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.script.Script
import com.dingyi.myluaapp.build.script.DefaultScript
import com.dingyi.myluaapp.common.ktx.toFile
import java.io.File

open class DefaultModule(
    private val project: Project,
    private val path: String
) : Module {
    override val type: String
        get() = "default"

    private val staticName = path.toFile().name

    override val name: String
        get() = staticName

    private val defaultBuilder = DefaultBuilder(this)


    private val defaultMainBuilderScript = DefaultScript(
        File(path, "build.gradle.lua").path
    )

    private val allScript = mutableListOf(defaultMainBuilderScript)


    private val dependencies = mutableListOf<Dependency>()

    override fun getBuilder(): Builder {
        return defaultBuilder
    }

    override fun init() {
        defaultMainBuilderScript.run()


    }


    override fun getDependencies(): List<Dependency> {
        return dependencies
    }

    override fun getPath(): String {
        return path
    }

    override fun getFileManager(): FileManager {
        return project.getFileManager()
    }

    override fun getProject(): Project {
        return project
    }

    override fun getLogger(): ILogger {
        return project.getLogger()
    }

    override fun getMavenRepository(): MavenRepository {
        return project.getMavenRepository()
    }

    override fun close() {
        allScript.forEach {
            it.close()
        }
    }

    override fun getMainBuilderScript(): Script {
        return defaultMainBuilderScript
    }

    override fun getAllScript(): List<Script> {
        return allScript
    }

    override fun getCache(): Cache {
        return project.getCache()
    }

    override fun toString(): String {
        return "DefaultModule(\npath='$path', \ntype='$type', \nname='$name', \ndefaultBuilder=$defaultBuilder, \ndefaultMainBuilderScript=$defaultMainBuilderScript, \nallScript=$allScript, \ndependencies=$dependencies)"
    }
}