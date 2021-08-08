package com.dingyi.myluaapp.ui.editor

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.database.service.ProjectService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/8/8 15:24
 * @description:
 **/
class MainPresenter(
    private val viewModel: MainViewModel,
    private val activity: AppCompatActivity
) : BasePresenter<MainViewModel>(viewModel, activity) {




    fun openProject(projectInfo: ProjectInfo) {
        activity.lifecycleScope.launch {
            val projectConfig= ProjectService.queryProjectConfig(projectInfo)

            viewModel.projectConfig.value=projectConfig
        }
    }

}