package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dingyi.myluaapp.core.project.ProjectController
import com.dingyi.myluaapp.core.project.ProjectFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/11/3 15:38
 * @description:
 **/
class MainViewModel: ViewModel() {

    var controller by Delegates.notNull<ProjectController>()

    private val _appTitle = MutableLiveData("")
    private val _openFiles = MutableLiveData<Pair<List<ProjectFile>,String>>()
    private val _openedDir = MutableLiveData<String>()
    val openFiles = _openFiles.map { it }
    val appTitle = _appTitle.map { it }
    val openedDir = _openedDir.map { it }

    fun initProjectController(projectPath:String) {
        controller = ProjectController(projectPath)
        _appTitle.value = controller.getProjectName()

    }

    fun refreshOpenedDir() {
        viewModelScope.launch {
            val path =
                withContext(Dispatchers.Default) { controller.getNowOpenedDir() }
            _openedDir.postValue(path)
        }
    }

    fun refreshOpenedFile() {
        viewModelScope.launch {

            controller.getOpenedFile().apply {
                _openFiles.postValue(this)
            }
        }
    }

    fun deleteFile(path: String) {
        viewModelScope.launch {
            controller.deleteFile(path)
            refreshOpenedDir()
            refreshOpenedFile()
        }
    }

    fun rename(path: String, toPath: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                controller.rename(path, toPath)
                refreshOpenedDir()
                refreshOpenedFile()
            }
        }
    }


}