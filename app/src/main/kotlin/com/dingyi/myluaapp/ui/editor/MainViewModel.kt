package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import java.io.File

class MainViewModel : ViewModel() {

    val project = MutableLiveData<Project>()

    suspend fun openProject(projectPath: File): Project {
        val project = PluginModule
            .getProjectService()
            .getProject(projectPath)

        this.project.value = project

        PluginModule
            .getEditorService()
            .loadEditorServiceState(project)

        return project
    }

}