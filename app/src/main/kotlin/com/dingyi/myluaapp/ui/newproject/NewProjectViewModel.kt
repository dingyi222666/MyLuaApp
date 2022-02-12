package com.dingyi.myluaapp.ui.newproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class NewProjectViewModel : ViewModel() {


    suspend fun getProjectTemplates() {
        val list = withContext(Dispatchers.IO) {
            PluginModule
                .getProjectService()
                .getTemplateList()
        }

        Log.e("list",list.toString())

        templates.postValue(list)
    }

    suspend fun newProject(template: ProjectTemplate, pair: MutablePair<String, String>) {
        if (pair.first.isEmpty()) {
            return R.string.new_project_app_name_error_empty
                .getString().showToast()

        }
        if (PluginModule
                .getProjectService().checkCreateProjectName(pair.first)
        ) {
            return R.string.new_project_app_name_error.getString().showToast()
        }
        if (pair.second.isEmpty()) {
            return R.string.new_project_app_package_error_empty
                .getString().showToast()
        }
        showProgressBar.value = true
        withContext(Dispatchers.IO) {
            template.create(
                projectPath = File(Paths.projectDir, pair.second),
                name = pair.first,
                packageName = pair.second
            )
        }

    }

    val appName = MutableLiveData<String>()
    val appPackageName = MutableLiveData<String>()


    val templates = MutableLiveData<List<ProjectTemplate>>()

    val showProgressBar = MutableLiveData(false)

}