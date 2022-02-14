package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel : ViewModel() {

    val allEditor = MutableLiveData<List<Editor<*>>>()
    val project = MutableLiveData<Project>()

    val currentEditor = MutableLiveData<Editor<*>?>()

    val subTitle = currentEditor.map { it ->
        it?.let { editor ->
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

    fun initEditor() {

        project.value?.let {
            PluginModule
                .getEditorService()
                .loadEditorServiceState(it)

            currentEditor.value = PluginModule
                .getEditorService()
                .getCurrentEditor()

            allEditor
                .value = PluginModule
                .getEditorService()
                .getAllEditor()


        }
    }

    fun refreshEditor() {

        PluginModule
            .getEditorService()
            .refreshEditorServiceState()

        currentEditor.value = PluginModule
            .getEditorService()
            .getCurrentEditor()

        allEditor
            .value = PluginModule
            .getEditorService()
            .getAllEditor()

    }


    fun openFile(path: String) {
        PluginModule
            .getEditorService()
            .openEditor(File(project.value?.path, path))

        refreshEditor()

    }

}