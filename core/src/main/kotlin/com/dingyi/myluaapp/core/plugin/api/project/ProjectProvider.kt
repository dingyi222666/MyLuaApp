package com.dingyi.myluaapp.core.plugin.api.project

interface ProjectProvider {

    fun createProject(projectPath:String):Project?
}