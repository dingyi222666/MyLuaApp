package com.dingyi.myluaapp.core.plugin.api.project

interface ProjectService {


    suspend fun getAllProject():List<Project>

    fun addProjectProvider(projectProvider: ProjectProvider)

}