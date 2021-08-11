package com.dingyi.myluaapp.ui.editor.presenter

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.database.service.ProjectService
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch

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
            val projectConfig = ProjectService.queryProjectConfig(projectInfo)
            viewModel.projectConfig.value = projectConfig
        }
    }

    fun openFile(path: String) {
        viewModel.projectConfig.value?.let {
            CodeFile().apply {
                projectConfig = it
                filePath = path
                openSelection = 0
                save()
            }
            it.update(it.id.toLong())
            println(it.openFiles)
            viewModel.projectConfig.value = it.copy()
        }

    }


}