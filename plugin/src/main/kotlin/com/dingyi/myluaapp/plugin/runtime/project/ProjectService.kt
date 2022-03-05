package com.dingyi.myluaapp.plugin.runtime.project

import com.dingyi.myluaapp.common.ktx.MutablePair
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.*
import com.dingyi.myluaapp.plugin.api.project.ProjectService
import java.io.File

class ProjectService(
    private val pluginContext: PluginContext
) : ProjectService {


    private val allProjectProvider = mutableListOf<ProjectProvider>()

    private val allCreateProjectProvider = mutableListOf<ProjectCreatorProvider>()

    override fun getAllProject(): Pair<List<Project>, String> {
        val errorBuilder = StringBuilder()
        return (Paths.projectDir.toFile()
            .listFiles()
            ?.sortedByDescending { it?.lastModified() }
            ?.mapNotNull {
                runCatching {
                    getProject(it)
                }.onFailure {
                    errorBuilder.append(it.stackTraceToString())
                }.getOrNull()
            } ?: listOf()) to errorBuilder.toString()
    }

    override fun addProjectProvider(projectProvider: ProjectProvider) {
        allProjectProvider.add(projectProvider)
    }

    override fun addProjectCreatorProvider(projectCreatorProvider: ProjectCreatorProvider) {
        allCreateProjectProvider.add(projectCreatorProvider)
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

    override fun getProject(projectPath: File): Project {
        for (projectProvider in allProjectProvider) {
            val project = projectProvider.indexProject(projectPath.path)
            if (project != null) {
                return project
            }
        }

        error("Unable to Index Project: $projectPath")
    }
}