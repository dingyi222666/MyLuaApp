package com.dingyi.myluaapp.ui.newproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.MutablePair
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showToast
import com.dingyi.myluaapp.core.project.ProjectBuilder

/**
 * @author: dingyi
 * @date: 2021/10/25 19:43
 * @description:
 **/
class MainViewModel : ViewModel() {

    suspend fun getProjectTemplates(projectBuilder: ProjectBuilder) {
        projectBuilder.getProjectTemplates().apply {
            templates.postValue(this)
        }
    }

    suspend fun newProject(pair: MutablePair<String, String>, projectBuilder: ProjectBuilder) {
        if (pair.first.isEmpty()) {
            return R.string.new_project_app_name_error_empty
                .getString().showToast()

        }
        if (projectBuilder.checkAppNameCanUse(pair.first)) {
            return R.string.new_project_app_name_error.getString().showToast()
        }
        if (pair.second.isEmpty()) {
            return R.string.new_project_app_package_error_empty
                .getString().showToast()
        }
        showProgressBar.value = true
        projectBuilder.createProject(pair)
    }

    val appName = MutableLiveData<String>()
    val appPackageName = MutableLiveData<String>()


    val templates = MutableLiveData<List<ProjectBuilder.ProjectTemplate>>()

    val showProgressBar = MutableLiveData(false)

}