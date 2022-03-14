package com.dingyi.myluaapp.plugin.api.project

interface ProjectCreatorProvider {


    fun getTemplates(): List<com.dingyi.myluaapp.plugin.api.project.ProjectTemplate>

}