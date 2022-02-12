package com.dingyi.myluaapp.plugin.runtime.project

import com.dingyi.myluaapp.common.kts.MutablePair
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.*
import com.dingyi.myluaapp.plugin.api.project.ProjectService
import java.io.File

class ProjectService(
    private val pluginContext: PluginContext
) : ProjectService {


    private val allProjectProvider = mutableListOf<ProjectProvider>()

    private val allCreateProjectProvider = mutableListOf<CreateProjectProvider>()

    override suspend fun getAllProject(): List<Project> {
        TODO("Not yet implemented")
    }

    override fun addProjectProvider(projectProvider: ProjectProvider) {
        allProjectProvider.add(projectProvider)
    }

    override fun addCreateProjectProvider(createProjectProvider: CreateProjectProvider) {
        allCreateProjectProvider.add(createProjectProvider)
    }

    override fun getTemplateList(): List<ProjectTemplate> {
        return allCreateProjectProvider.flatMap { it.getTemplates() }
    }

    override fun getDefaultCreateProjectInfo(): MutablePair<String, String> {
        val size = (Paths.projectDir.toFile().listFiles() ?: arrayOf<File>())
            .map { it.name }
            .filter {
                it.startsWith("MyApplication")
            }.size


        val name = "MyApplication" + (if (size > 0) (size + 1).toString() else "")

        return MutablePair(name, "com.MyLuaApp.application")
    }

    override fun checkCreateProjectName(name: String): Boolean {
        return (Paths.projectDir + "/$name").toFile().isDirectory &&
                (Paths.projectDir + "/$name").toFile().absolutePath !=
                Paths.projectDir.toFile().absolutePath
    }
}