package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.core.helper.ProgressMonitor
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.helper.TreeHelper
import com.dingyi.myluaapp.view.treeview.TreeNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel : ViewModel() {

    val logBroadcastReceiver = MutableLiveData<LogBroadcastReceiver>()
    val allEditor = MutableLiveData<Pair<List<Editor>,Editor?>>()
    val project = MutableLiveData<Project>()

    val rootNode = MutableLiveData<TreeNode>()

    val subTitle = allEditor.map { it ->
        it.second?.let { editor ->
            project.value?.let {
                editor.getFile()
                    .path.substring(it.path.path.length + 1)
            } ?: ""
        } ?: ""
    }

    val progressMonitor = ProgressMonitor(viewModelScope)

    fun openProject(projectPath: File): Project {
        val project = PluginModule
            .getProjectService()
            .getProject(projectPath)

        this.project.value = project


        return project
    }

    private fun refresh() {

        allEditor
            .value =
            PluginModule.getEditorService().let { it.getAllEditor() to it.getCurrentEditor() }

    }

    suspend fun initEditor() = withContext(Dispatchers.IO) {

        project.value?.let {
            PluginModule
                .getEditorService()
                .loadEditorServiceState(it)

           withContext(Dispatchers.Main) { refresh() }

        }
    }

    fun refreshEditor() {

        PluginModule
            .getEditorService()
            .refreshEditorServiceState()

        refresh()
    }


    fun openFile(path: String) {

        var file = File(path)

        if (!file.exists()) {
            file = File(project.value?.path, path)
        }

        if (allEditor.value?.second?.getFile() == file) {
            return
        }

        PluginModule
            .getEditorService()
            .openEditor(file)

        refreshEditor()

    }

    fun refreshFileList() {
        progressMonitor.postAsyncTask {
            it.changeProgressState(true)
            val allNode = withContext(Dispatchers.IO) {
                TreeHelper.getAllNode(
                    project.value?.path
                )
            }
            withContext(Dispatchers.Main) {
                rootNode.value = allNode
            }
            it.changeProgressState(false)
        }
    }




}