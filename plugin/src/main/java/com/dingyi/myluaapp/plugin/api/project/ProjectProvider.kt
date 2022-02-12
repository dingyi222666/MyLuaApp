package com.dingyi.myluaapp.plugin.api.project

interface ProjectProvider {

    fun indexProject(projectPath:String): com.dingyi.myluaapp.plugin.api.project.Project?
}