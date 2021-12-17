package com.dingyi.myluaapp.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.bean.Poetry
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.getStringArray
import com.dingyi.myluaapp.common.kts.showSnackBar
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.core.project.ProjectManager
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.network.Apis
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
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


    val poetry = MutableLiveData<String>()

    private fun showErrorMessage(binding: ActivityMainBinding) {
        R.string.main_project_list_config_error.getString()
            .showSnackBar(binding.root)
    }

    fun refreshPoetry(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.scopeNetLife {
            (runCatching {
                Get<Poetry>(Apis.poetryApi).await()
            }.getOrNull()?.content ?: R.array.main_poetry_array.getStringArray().run {
                get(IntRange(0, lastIndex).random())
            }).let {
                poetry.postValue(it)
            }
        }
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