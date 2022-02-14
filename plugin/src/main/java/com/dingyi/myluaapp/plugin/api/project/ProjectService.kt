package com.dingyi.myluaapp.plugin.api.project

import com.dingyi.myluaapp.common.kts.MutablePair
import java.io.File

interface ProjectService {


    fun getAllProject():List<Project>

    fun addProjectProvider(projectProvider: ProjectProvider)

    fun addCreateProjectProvider(createProjectProvider: CreateProjectProvider)

    fun getTemplateList():List<ProjectTemplate>

    fun getDefaultCreateProjectInfo():MutablePair<String,String>
    fun checkCreateProjectName(name: String): Boolean

    fun getProject(projectPath:File):Project

}