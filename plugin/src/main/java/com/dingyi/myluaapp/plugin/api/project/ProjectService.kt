package com.dingyi.myluaapp.plugin.api.project

import com.dingyi.myluaapp.common.kts.MutablePair

interface ProjectService {


    suspend fun getAllProject():List<Project>

    fun addProjectProvider(projectProvider: ProjectProvider)

    fun addCreateProjectProvider(createProjectProvider: com.dingyi.myluaapp.plugin.api.project.CreateProjectProvider)

    fun getTemplateList():List<ProjectTemplate>

    fun getDefaultCreateProjectInfo():MutablePair<String,String>
    fun checkCreateProjectName(name: String): Boolean


}