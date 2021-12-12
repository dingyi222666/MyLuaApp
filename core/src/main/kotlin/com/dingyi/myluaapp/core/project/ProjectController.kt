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
class ProjectController(private val path: String) {

    val project = Project(path)


    fun getProjectName(): String {
        return project.generateAppProject()?.appName ?: ""

    }


    suspend fun getOpenedFile() = withContext(Dispatchers.Default) {
        project.getOpenedFiles()
    }


}