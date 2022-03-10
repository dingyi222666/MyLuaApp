package com.dingyi.myluaapp.ui.editor

import androidx.lifecycle.*
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.showToast
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.core.helper.ProgressMonitor
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.FileTemplate
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.helper.TreeHelper
import com.dingyi.myluaapp.view.treeview.TreeNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel : ViewModel() {


    val logBroadcastReceiver = MutableLiveData<LogBroadcastReceiver>()

    val allEditor = MutableLiveData<List<Editor>>()

    val project = MutableLiveData<Project>()

    val rootNode = MutableLiveData<TreeNode>()

    val currentEditor = MutableLiveData<Editor?>()


    val subTitle = currentEditor.map { it ->
        it?.let { editor ->
            project.value?.let {
                editor.getFile()
                    .path.substring(it.path.path.length + 1)
            } ?: ""
        } ?: ""
    }

    val progressMonitor = ProgressMonitor()

    fun openProject(projectPath: File): Project {
        val project = PluginModule
            .getProjectService()
            .getProject(projectPath)

        this.project.value = project


        return project
    }

    private fun refresh() {

        PluginModule.getEditorService().let { editorService ->
            currentEditor.value = editorService.getCurrentEditor()

            allEditor.value = editorService.getAllEditor()
        }


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

        currentEditor.value = PluginModule
            .getEditorService()
            .openEditor(file)

        refreshEditor()

    }

    fun refreshFileList() {
        progressMonitor.postAsyncTask {

            val allNode = withContext(Dispatchers.IO) {
                TreeHelper.getAllNode(
                    project.value?.path
                )
            }
            withContext(Dispatchers.Main) {
                rootNode.value = allNode
            }

        }
    }

    fun setCurrentEditor(path: String) {
        var file = File(path)

        if (!file.exists()) {
            file = File(project.value?.path, path)
        }

        PluginModule
            .getEditorService()
            .setCurrentEditor(file)

        currentEditor.value = PluginModule.getEditorService().getCurrentEditor()


    }

    suspend fun deleteFile(file: File) {
        project
            .value
            ?.deleteFile(file)
    }

    suspend fun renameFile(file: File, targetFile: File) {
        project
            .value
            ?.renameFile(file, targetFile)
    }

    suspend fun createFile(fileTemplate: FileTemplate, file: File, inputName: String) {
        fileTemplate.create(file, inputName)
    }

    suspend fun createDirectory(targetPath: File) {
        project
            .value
            ?.createDirectory(targetPath)
    }

    fun updateNode(treeNode: TreeNode) {
        progressMonitor.postAsyncTask {

            val newTreeNode = withContext(Dispatchers.IO) {
                if (treeNode.parent.isRoot) {
                    TreeHelper.updateNode(treeNode)
                } else {
                    TreeHelper.updateNode(treeNode.parent)
                }
            }.checkNotNull()

            withContext(Dispatchers.Main) {
                refreshEditor()
           
                if (treeNode == rootNode.value) {
                    rootNode.value = newTreeNode
                } else {
                    rootNode.value = rootNode.value
                }
            }
        }


    }

    fun saveEditor() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PluginModule
                    .getEditorService()
                    .saveEditorServiceState()
            }

            R.string.editor_menu_save_success
                .getString().showToast()

        }
    }
}