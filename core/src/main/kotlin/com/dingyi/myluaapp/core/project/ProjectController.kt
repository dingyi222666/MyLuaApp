package com.dingyi.myluaapp.core.project

/**
 * @author: dingyi
 * @date: 2021/11/16 17:27
 * @description:
 **/
class ProjectController(private val path:String) {

    val project = Project(path)

    fun getProjectName(): String {
        return project.generateAppProject()?.appName ?: ""
    }
}