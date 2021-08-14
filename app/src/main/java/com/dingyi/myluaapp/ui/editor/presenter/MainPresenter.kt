package com.dingyi.myluaapp.ui.editor.presenter

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.database.service.ProjectService
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import livedatabus.LiveDataBus
import java.util.*

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
                openSelectionColumn= 0
                openSelectionLine= 0
                save()
            }
            it.update(it.id.toLong())
            viewModel.projectConfig.value = it.copy()
        }

    }

    fun saveAllFile() {


        viewModel.projectConfig.value?.let { projectConfig ->
            projectConfig.openFiles.forEach {
                it.filePath.toFile().writeText(it.code)
            }
        }

        LiveDataBus
            .getDefault()
            .with("saveAllFile", String::class.java)
            .postValue(UUID.randomUUID().toString())


    }

    fun showTabMenu(tab: TabLayout.Tab) {
        PopupMenu(tab.view.context, tab.view)
            .apply {
                inflate(R.menu.editor_tab)
                viewModel.projectConfig.value?.let { projectConfig ->
                    if (projectConfig.openFiles.size<2) {
                        menu.findItem(R.id.editor_action_close).isVisible=false
                        menu.findItem(R.id.editor_action_close_other).isVisible=false
                    }
                }
            }
            .show()
    }


}