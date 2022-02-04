package com.dingyi.myluaapp.build.modules.android.module

import android.util.Log
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.script.Script
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.dependency.FileDependency
import com.dingyi.myluaapp.build.dependency.ProjectDependency
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.script.DefaultScript
import com.dingyi.myluaapp.common.kts.toFile
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import java.io.File

class AndroidModule(
    private val project: Project,
    private val path: String
) : Module {
    override val type: String
        get() = _type

    private var _type = "AndroidApplication"

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
        return project.getMainBuilder().getServiceRepository()
            .onCreateBuilder(path,this) ?: defaultBuilder
    }

    override fun init() {
        defaultMainBuilderScript.run()


        val table = defaultMainBuilderScript.get("plugins")

        if (table is LuaTable) {
            for (key in table.keys()) {
                when (table[key].tojstring()) {
                    "com.android.library" -> {
                        _type = "AndroidLibrary"
                        break
                    }
                    "com.android.application" -> {
                        _type = "AndroidApplication"
                        break
                    }
                }

            }
        }

        val applicationId = if (_type == "AndroidApplication") {
            (defaultMainBuilderScript.get("android.defaultConfig.applicationId") as LuaValue?)?.tojstring()
        } else {
            null
        }


        getCache().putCache(
            "${name}_build_config",
            BuildConfig(
                applicationId = applicationId,
                buildVariants = getCache().getCache("build_mode")
            )
        )


        initDependencies()

    }

    private fun initDependencies() {

        for (scriptDependencies in arrayOf(
            defaultMainBuilderScript.get("dependencies.implementation"),
            defaultMainBuilderScript.get("dependencies.api")
        )) {
            if (scriptDependencies is LuaTable) {
                for (key in scriptDependencies.keys()) {
                    Log.e("fuck", "$key ${key.tojstring()}")
                    when (key.tojstring()) {
                        "fileTree" -> {
                            initDependencyFileTree(scriptDependencies[key])
                        }
                        "project" -> {
                            initDependencyModule(scriptDependencies[key])
                        }
                        else -> {
                            initMavenDependency(scriptDependencies[key])
                        }
                    }
                }
            }
        }
    }

    private fun initMavenDependency(value: LuaValue?) {
        if (value is LuaString) {
            dependencies.addAll(getMavenRepository().getDependency(value.tojstring()))
        }
    }

    private fun initDependencyModule(value: LuaValue?) {
        if (value is LuaTable) {
            for (key in value.keys()) {
                value[key]?.let { _value ->
                    val name = _value.tojstring()
                        .let {
                            it.substring(it.lastIndexOf('/')+1)
                        }

                    if (getProject().getModule(name) != null) {
                        dependencies.add(ProjectDependency(name))
                    } else {
                        throw CompileError("Not Found Project Dependency:$name")
                    }
                }
            }
        }
    }

    private fun initDependencyFileTree(value: LuaValue?) {
        if (value is LuaTable) {
            for (key in value.keys()) {
                value[key]?.let { _value ->
                    val name = _value.tojstring()
                    if (name.toFile().isFile) {
                        dependencies.add(FileDependency(name))
                    } else {
                        throw CompileError("Not Found File Dependency:$name")
                    }
                }
            }
        }
    }


    override fun getDependencies(): List<Dependency> {
        return dependencies
    }

    override fun getPath(): String {
        return path
    }

    override fun getCache(): Cache {
        return project.getCache()
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

    override fun toString(): String {
        return "AndroidModule(\npath='$path', \ntype='$type', \nname='$name', \ndefaultBuilder=$defaultBuilder, \ndefaultMainBuilderScript=$defaultMainBuilderScript, \nallScript=$allScript, \ndependencies=$dependencies)"
    }
}