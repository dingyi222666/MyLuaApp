package com.dingyi.myluaapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showSnackBar
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.core.project.ProjectManager
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/10/22 23:37
 * @description:
 **/
class MainViewModel : ViewModel() {


    private val projectManger = ProjectManager(Paths.projectDir)

    val projectList = MutableLiveData<List<Project.AppProject>>()


    private fun showErrorMessage(binding: ActivityMainBinding) {
        R.string.main_project_list_config_error.getString()
            .showSnackBar(binding.root)
    }

    suspend fun refreshProjectList(binding: ActivityMainBinding) = withContext(Dispatchers.Main) {
        val (errorMessage, realProjectList) = projectManger.getProjectList()
        if (errorMessage != null) {
            showErrorMessage(binding)
        }
        val result = mutableListOf<Project.AppProject>()
        withContext(Dispatchers.IO) {
            realProjectList.map {
                it.generateAppProject()
            }.forEach {
                if (it == null) {
                    showErrorMessage(binding)
                } else {
                    result.add(it)
                }
            }
        }
        projectList.postValue(result)
    }

}