package com.dingyi.myluaapp.plugin.api.project

interface CreateProjectProvider {


    fun getTemplates(): List<com.dingyi.myluaapp.plugin.api.project.ProjectTemplate>

}