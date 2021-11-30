package com.dingyi.myluaapp.ui.editior

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.core.project.ProjectController
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/11/3 15:38
 * @description:
 **/
class MainViewModel: ViewModel() {

    private var controller by Delegates.notNull<ProjectController>()

    private val _appTitle = MutableLiveData("")

    val appTitle : LiveData<String> = _appTitle

    fun initProjectController(projectPath:String) {
        controller = ProjectController(projectPath)

        _appTitle.value = controller.getProjectName()

    }


}