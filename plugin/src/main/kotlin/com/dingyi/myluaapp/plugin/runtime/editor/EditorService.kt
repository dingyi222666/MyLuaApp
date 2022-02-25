package com.dingyi.myluaapp.plugin.runtime.editor

import android.util.Log
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import com.dingyi.myluaapp.plugin.api.editor.EditorService
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.net.NetworkInterface

class EditorService(private val pluginContext: PluginContext) : EditorService {

    private lateinit var currentProject: Project

    private val allEditor = mutableListOf<Editor>()

    private val allEditorProvider = mutableListOf<EditorProvider>()

    private var currentEditor: Editor? = null

    private val supportLanguages = mutableListOf<String>()

    private lateinit var currentEditorServiceState: EditorServiceState

    override fun  getCurrentEditor(): Editor? {
        return currentEditor
    }

    override fun getAllEditor(): List<Editor> {
        return allEditor
    }

    override fun addEditorProvider(editorProvider: EditorProvider) {
        allEditorProvider.add(editorProvider)
    }

    override fun openEditor(editorPath: File): Editor? {

        if (!editorPath.isFile) {
            throw FileNotFoundException("The file ${editorPath.path} was deleted.")
        }


        val findEditor = allEditor.find { it.getFile().path == editorPath.path }

        if (findEditor!=null) {
            currentEditor = findEditor
            currentEditorServiceState.lastOpenPath = findEditor.getFile().path
            return currentEditor
        }

        for (it in allEditorProvider) {
            val editor = it.createEditor(editorPath)
            if (editor != null) {
                allEditor.add(editor)
                currentEditor = editor
                currentEditorServiceState.lastOpenPath = editorPath.path

                val find =
                    currentEditorServiceState.editors.find { it.path == editorPath.path }

                if (find == null) {
                    currentEditorServiceState.editors.add(editor.saveState())
                }
                return editor
            }
        }
        return null
    }

    override fun closeEditor(editor: Editor) {
        val indexOfEditor = allEditor.indexOf(editor)

        val targetIndex = when {
            indexOfEditor + 1 == allEditor.size && indexOfEditor != 0 -> indexOfEditor - 1
            indexOfEditor == 0 && allEditor.size == 1 -> null
            indexOfEditor == -1 -> null
            allEditor.size == 1 -> null
            else -> indexOfEditor
        }

        allEditor.removeAt(indexOfEditor)

        currentEditor = allEditor.getOrNull(targetIndex ?: 0)

        currentEditorServiceState.lastOpenPath = currentEditor?.getFile()?.path

        currentEditorServiceState.editors.removeIf { it.path == editor.getFile().path }


    }


    override fun closeEditor(editor: File) {
        val indexOfEditor = currentEditorServiceState
            .editors.indexOf(currentEditorServiceState.editors.find { it.path == editor.path })

        val targetIndex = when {
            indexOfEditor + 1 == currentEditorServiceState.editors.size && indexOfEditor != 0 -> indexOfEditor - 1
            indexOfEditor == 0 && currentEditorServiceState.editors.size == 1 -> null
            indexOfEditor == -1 -> null
            currentEditorServiceState.editors.size == 1 -> null
            else -> indexOfEditor
        }

        runCatching {
            currentEditorServiceState.editors.removeAt(indexOfEditor)
        }

        val currentState = currentEditorServiceState.editors.getOrNull(targetIndex ?: 0)

        currentEditorServiceState.lastOpenPath = currentState?.path

        allEditor.removeIf { it.getFile().path == editor.path }

        currentEditor = allEditor.getOrNull(targetIndex ?: 0)


    }

    override fun clearAllEditor() {
        allEditor.clear()
    }

    override fun saveEditorServiceState() {

        currentEditorServiceState.editors.clear()

        allEditor.forEach {
            currentEditorServiceState.editors.add(it.saveState() as EditorState)
        }

        Log.e("all", currentEditorServiceState.toString())


        val projectEditorStateFile = File(currentProject.path, ".MyLuaApp/editor_config.bin")

        val text = Gson()
            .toJson(currentEditorServiceState)


        if (!projectEditorStateFile.isFile) {
            projectEditorStateFile.parentFile?.mkdirs()
            projectEditorStateFile.createNewFile()
        }

        // projectEditorStateFile.outputStream().writeUseGZIP(text)

        projectEditorStateFile.writeText(text)


    }

    override fun refreshEditorServiceState() {
        for (i in currentEditorServiceState.editors.indices) {

            val editorState = currentEditorServiceState.editors[i]

            val file = editorState.path.toFile()

            if (!file.exists()) {
                pluginContext
                    .getActionService()
                    .callAction<Unit>(
                        pluginContext
                            .getActionService()
                            .createActionArgument()
                            .addArgument(file), DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST
                    )
                closeEditor(file)
                continue
            }

            val findEditor = allEditor.find { it.getFile().path == editorState.path }

            if (findEditor != null) {
                if (currentEditorServiceState.lastOpenPath == editorState.path) {
                    currentEditor = findEditor
                }
                continue
            }

            for (it in allEditorProvider) {
                val editor = it.createEditor(file)
                if (editor != null) {
                    editor.restoreState(editorState)

                    if (currentEditorServiceState.lastOpenPath == editorState.path) {
                        currentEditor = editor
                    }

                    allEditor.add(i - 1, editor)
                    break
                }
            }
        }

    }

    override fun loadEditorServiceState(project: Project) {
        currentProject = project

        val projectEditorStateFile = File(project.path, ".MyLuaApp/editor_config.bin")


        currentEditorServiceState = runCatching {
            Gson()
                .fromJson(
                    projectEditorStateFile.readText()
                    //projectEditorStateFile.inputStream().readFormGZIP()
                    , getJavaClass<EditorServiceState>()
                )
        }.getOrElse {

            Log.e("error", it.stackTraceToString())

            EditorServiceState(
                lastOpenPath = null,
                editors = mutableListOf()
            )
        }


        //index all editor

        Log.e("test", currentEditorServiceState.toString())

        indexAllEditor()

        Log.e("all", allEditorProvider.toString())

    }

    private fun indexAllEditor() {

        for (editorState in currentEditorServiceState.editors) {

            val file = editorState.path.toFile()

            if (!file.exists()) {
                pluginContext
                    .getActionService()
                    .callAction<Unit>(
                        pluginContext
                            .getActionService()
                            .createActionArgument()
                            .addArgument(file), DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST
                    )
                closeEditor(file)
                continue
            }

            val findEditor = allEditor.find { it.getFile().path == editorState.path }

            if (findEditor != null) {
                if (currentEditorServiceState.lastOpenPath == editorState.path) {
                    currentEditor = findEditor
                }
                continue
            }

            for (it in allEditorProvider) {
                val editor = it.createEditor(file)
                if (editor != null) {
                    editor.restoreState(editorState)

                    if (currentEditorServiceState.lastOpenPath == editorState.path) {
                        currentEditor = editor
                    }

                    allEditor.add(editor)
                    break
                }
            }
        }


    }

    override fun closeOtherEditor(editor: Editor) {
        currentEditorServiceState
            .editors
            .clear()

        currentEditorServiceState.editors
            .add(editor.saveState())

        currentEditorServiceState.lastOpenPath = editor.getFile().path
        allEditor.clear()
        allEditor.add(editor)
    }

    override fun getEditor(filePath: File): Editor? {
        return allEditor.find { it.getFile().path == filePath.path }
    }

    override fun getSupportLanguages(): List<String> {
        return supportLanguages
    }

    override fun addSupportLanguages(vararg language: String) {
        supportLanguages.addAll(language)
    }

    override fun setCurrentEditor(file: File) {
        currentEditor = openEditor(file)
    }

    override fun closeAllEditor() {
        currentEditorServiceState.editors.clear()
        allEditor.clear()
        currentEditor = null
        currentEditorServiceState.lastOpenPath = null
        saveEditorServiceState()
    }
}