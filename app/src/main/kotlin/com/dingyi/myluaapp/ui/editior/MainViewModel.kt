package com.dingyi.myluaapp.ui.editior

import androidx.lifecycle.*
import com.dingyi.myluaapp.core.project.ProjectController
import com.dingyi.myluaapp.core.project.ProjectFile
import kotlin.properties.Delegates
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

/**
 * @author: dingyi
 * @date: 2021/11/3 15:38
 * @description:
 **/
class MainViewModel: ViewModel() {

    var controller by Delegates.notNull<ProjectController>()

    private val _appTitle = MutableLiveData("")
    private val _openFiles = MutableLiveData<Pair<List<ProjectFile>,String>>()
    private val _nowOpenFile = MutableLiveData("")

    val openFiles = _openFiles.map { it }
    val appTitle = _appTitle.map { it }

    fun initProjectController(projectPath:String) {
        controller = ProjectController(projectPath)

        _appTitle.value = controller.getProjectName()



    }


    fun refreshOpenedFile() {
        viewModelScope.launch {
            controller.getOpenedFile().apply {
                _openFiles.postValue(this)
            }
        }
    }



}