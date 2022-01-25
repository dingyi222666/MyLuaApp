package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.common.kts.toFile
import java.io.File

class ProjectDependency(
    val path: String
) : Dependency {

    private val file = path.toFile()

    override val name: String
        get() = file.name
    override val type: String
        get() = "module"

    override fun getDependenciesFile(): Set<File> {
        return setOf()
    }

    fun getModule(project: Project): Module {
        return project.getModule(name) ?: throw Exception("Can't find module")
    }
}