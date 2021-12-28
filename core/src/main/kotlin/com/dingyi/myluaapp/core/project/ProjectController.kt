package com.dingyi.myluaapp.core.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @author: dingyi
 * @date: 2021/11/16 17:27
 * @description:
 **/
class ProjectController(val projectPath: String) {


    private val project = Project(projectPath)


    fun getProjectName(): String {
        return project.generateAppProject()?.appName ?: ""

    }

    suspend fun getOpenedFile() = withContext(Dispatchers.Default) {
        project.getOpenedFiles()
    }

    fun selectOpenedFile(it: String) {
        project.selectOpenedFile(it)
    }

    fun getProjectFile(): Project {
        return project
    }

    val closeOtherFile = project::closeOtherOpenedFile

    val getNowOpenedDir = project::getNowOpenedDir

    val closeFile = project::closeOpenedFile

    val openFile = project::openFile
    val postNowOpenedDir = project::postNowOpenedDir

    val getFileTemplates = project::getFileTemplates

    val createTemplateFile = project::createTemplateFile
}