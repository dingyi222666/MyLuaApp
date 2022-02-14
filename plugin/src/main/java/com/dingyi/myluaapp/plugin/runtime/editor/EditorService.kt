package com.dingyi.myluaapp.plugin.runtime.editor

import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.readFormGZIP
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.writeUseGZIP
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import com.dingyi.myluaapp.plugin.api.editor.EditorService
import com.dingyi.myluaapp.plugin.api.project.Project
import com.google.gson.Gson
import java.io.File

class EditorService : EditorService {

    private lateinit var currentProject: Project

    private val allEditor = mutableListOf<Editor<*>>()

    private val allEditorProvider = mutableListOf<EditorProvider>()

    private var currentEditor: Editor<*>? = null

    private lateinit var currentEditorServiceState: EditorServiceState

    override fun  getCurrentEditor(): Editor<*>? {
        return currentEditor
    }

    override fun getAllEditor(): List<Editor<*>> {
        return allEditor
    }

    override fun addEditorProvider(editorProvider: EditorProvider) {
        allEditorProvider.add(editorProvider)
    }

    override fun openEditor(editorPath: File): Editor<*>? {
        for (it in allEditorProvider) {
            val editor = it.createEditor(editorPath)
            if (editor != null) {

                allEditor.add(editor)
                currentEditor = editor
                currentEditorServiceState.editors.add(editor.saveState() as EditorState)
                return editor
            }
        }
        return null
    }

    override fun closeEditor(editor: Editor<*>) {
        val indexOfEditor = allEditor.indexOf(editor)

        val targetIndex = when {
            indexOfEditor == allEditor.size -> indexOfEditor - 1

            indexOfEditor == 0 && allEditor.size == 1 -> null
            else -> indexOfEditor
        }

        allEditor.removeAt(indexOfEditor)

        currentEditor = allEditor.getOrNull(targetIndex ?: 0)

        currentEditorServiceState.lastOpenPath = currentEditor?.getFile()?.path

        currentEditorServiceState.editors.removeIf { it.path == editor.getFile().path }


    }

    override fun closeAllEditor() {
        allEditor.clear()
    }

    override fun saveEditorServiceState() {
        val projectEditorStateFile = File(currentProject.path, ".MyLuaApp/editor_config.json")

        val text = Gson()
            .toJson(currentEditorServiceState)


        projectEditorStateFile.outputStream().writeUseGZIP(text)

        allEditor.clear()


    }

    override fun loadEditorServiceState(project: Project) {
        currentProject = project

        val projectEditorStateFile = File(project.path, ".MyLuaApp/editor_config.json")

        currentEditorServiceState = if (projectEditorStateFile.isFile) {
            Gson()
                .fromJson(
                    projectEditorStateFile.inputStream().readFormGZIP(), getJavaClass()
                )

        } else {
            EditorServiceState(
                lastOpenPath = null,
                editors = mutableListOf()
            )
        }

        //index all editor

        indexAllEditor()

    }

    private fun indexAllEditor() {
        currentEditorServiceState.editors.forEach { editorState ->
            for (it in allEditorProvider) {
                val editor = it.createEditor(editorState.path.toFile())
                if (editor != null) {
                    (editor as Editor<EditorState>).restoreState(editorState)
                    allEditor.add(editor)

                    if (currentEditorServiceState.lastOpenPath == editorState.path) {
                        currentEditor = editor
                    }
                    break
                }
            }
        }

        if (currentEditor == null && allEditor.isNotEmpty()) {
            currentEditor = allEditor[0]
        }

    }

    override fun closeOtherEditor(editor: Editor<*>) {
        currentEditorServiceState
            .editors
            .clear()

        currentEditorServiceState.editors
            .add(editor.saveState() as EditorState)

        currentEditorServiceState.lastOpenPath = editor.getFile().path
    }

    override fun getEditor(filePath: File): Editor<*>? {
        return allEditor.find { it.getFile().path == filePath.path }
    }
}