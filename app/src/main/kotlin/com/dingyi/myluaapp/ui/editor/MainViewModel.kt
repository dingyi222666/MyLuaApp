package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import java.io.File

class MainViewModel : ViewModel() {

    val allEditor = MutableLiveData<Pair<List<Editor>,Editor?>>()
    val project = MutableLiveData<Project>()


    val subTitle = allEditor.map { it ->
        it.second?.let { editor ->
            project.value?.let {
                editor.getFile()
                    .path.substring(it.path.path.length + 1)
            }
        } ?: ""
    }

    fun openProject(projectPath: File): Project {
        val project = PluginModule
            .getProjectService()
            .getProject(projectPath)

        this.project.value = project


        return project
    }

    private fun refresh() {

        allEditor
            .value = PluginModule
            .getEditorService()
            .getAllEditor() to  PluginModule
            .getEditorService()
            .getCurrentEditor()

    }

    fun initEditor() {

        project.value?.let {
            PluginModule
                .getEditorService()
                .loadEditorServiceState(it)

            refresh()

        }
    }

    fun refreshEditor() {

        PluginModule
            .getEditorService()
            .refreshEditorServiceState()

        refresh()
    }


    fun openFile(path: String) {
        PluginModule
            .getEditorService()
            .openEditor(File(project.value?.path, path))

        refresh()

    }

}