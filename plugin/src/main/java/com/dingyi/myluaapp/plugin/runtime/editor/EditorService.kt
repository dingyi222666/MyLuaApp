package com.dingyi.myluaapp.plugin.runtime.editor

import android.util.Log
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import com.dingyi.myluaapp.plugin.api.editor.EditorService
import com.dingyi.myluaapp.plugin.api.project.Project
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException

class EditorService : EditorService {

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

        for (openedEditor in allEditor) {
            if (openedEditor.getFile().absolutePath == editorPath.absolutePath) {
                currentEditor = openedEditor
                currentEditorServiceState.lastOpenPath = openedEditor.getFile().path
                return currentEditor
            }
        }

        for (it in allEditorProvider) {
            val editor = it.createEditor(editorPath)
            if (editor != null) {
                allEditor.add(editor)
                currentEditor = editor
                currentEditorServiceState.lastOpenPath = editor.getFile().absolutePath

                val find =
                    currentEditorServiceState.editors.find { it.path == editorPath.absolutePath }

                if (find == null) {
                    currentEditorServiceState.editors.add(editor.saveState() as EditorState)
                }
                return editor
            }
        }
        return null
    }

    override fun closeEditor(editor: Editor) {
        val indexOfEditor = allEditor.indexOf(editor)

        val targetIndex = when {
            indexOfEditor == allEditor.size -> indexOfEditor - 1

            indexOfEditor == 0 && allEditor.size == 1 -> null
            else -> indexOfEditor
        }

        allEditor.removeAt(indexOfEditor)

        currentEditor = allEditor.getOrNull(targetIndex ?: 0)

        currentEditorServiceState.lastOpenPath = currentEditor?.getFile()?.absolutePath

        currentEditorServiceState.editors.removeIf { it.path == editor.getFile().absolutePath }


    }

    override fun clearAllEditor() {
        allEditor.clear()
    }

    override fun saveEditorServiceState() {

        currentEditorServiceState.editors.clear()

        Log.e("test", currentEditorServiceState.toString())



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
        allEditor.clear()
        indexAllEditor()
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


            val findEditor = allEditor.find { it.getFile().absolutePath == editorState.path }

            if (findEditor != null) {
                if (currentEditorServiceState.lastOpenPath == editorState.path) {
                    currentEditor = findEditor
                }
                continue
            }

            for (it in allEditorProvider) {
                val editor = it.createEditor(editorState.path.toFile())
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
}