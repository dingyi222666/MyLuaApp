package com.dingyi.myluaapp.plugin.modules.default.editor

import android.content.Context
import android.view.View
import com.dingyi.myluaapp.common.kts.toFile
import io.github.rosemoe.sora.widget.CodeEditor

import com.dingyi.myluaapp.plugin.api.editor.Editor

import com.dingyi.myluaapp.plugin.api.editor.language.Language
import com.dingyi.myluaapp.plugin.modules.default.editor.language.EmptyLanguage
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
class Editor(

    private val path: File,
    private val id: Int
) : Editor {


    private var currentLanguage: Language = EmptyLanguage()

    private lateinit var currentEditor: CodeEditor

    private var currentEditorState = EditorState(path.path, 0, 0, 0, 0, 0f)

    private var currentText: CharSequence = ""

    override fun getText(): CharSequence {
        return currentEditor.text
    }

    override fun getCurrentLine(): Int {
        return currentEditor.text.cursor.leftLine
    }

    override fun getCurrentColumn(): Int {
        return currentEditor.text.cursor.leftColumn
    }

    override fun setText(charSequence: CharSequence) {
        currentText = charSequence
        currentEditor.setText(charSequence)
    }

    override fun appendText(charSequence: CharSequence) {
        currentEditor.text.beginBatchEdit()
        currentEditor.text.insert(getCurrentLine(), getCurrentColumn(), charSequence)
        currentEditor.text.endBatchEdit()
    }


    override fun getId(): Int {
        return id
    }


    override fun saveState(): EditorState {

        if (this::currentEditor.isInitialized) {

            currentEditorState = EditorState(
                path = path.path,
                line = getCurrentLine(),
                column = getCurrentColumn(),
                scrollX = currentEditor.scroller.currX,
                scrollY = currentEditor.scroller.currY,
                textSize = currentEditor.textSizePx
            )
        }

        return currentEditorState
    }

    override fun restoreState(editorState: EditorState) {
        currentEditorState = editorState
    }

    private fun doRestoreState(editorState: EditorState) {
        currentEditor.apply {
            text.cursor.set(editorState.line, editorState.column)
            scroller.startScroll(
                scroller.currX,
                scroller.currY,
                editorState.scrollX - scroller.currX,
                editorState.scrollY - scroller.currY, 0
            )
            if (editorState.textSize > 0) {
                textSizePx = editorState.textSize
            }
        }
    }


    override fun getFile(): File {
        return path
    }

    override fun isModify(): Boolean {
        return currentEditor.text.length == currentText.length
    }

    override fun getLanguage(): Language {
        return currentLanguage
    }

    override fun setLanguage(language: Language) {
        currentLanguage = language
    }

    override fun getCurrentView(): View {
        return currentEditor
    }

    override fun save() {

        if (!::currentEditor.isInitialized) {
            return
        }

        if (!path.isFile) {
            throw FileNotFoundException("The File was deleted.")
        }
        path.writeText(currentEditor.text.toString())
    }

    override fun binCurrentView(r: CodeEditor) {
        currentEditor = r
    }

    override suspend fun read():Unit = withContext(Dispatchers.IO) {
        if (!::currentEditor.isInitialized) {
            return@withContext
        }
        if (!path.isFile) {
            throw FileNotFoundException("The File was deleted.")
        }
        val text = path.readText()
        withContext(Dispatchers.Main) {
            currentEditor.setText(text)
            doRestoreState(currentEditorState)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as com.dingyi.myluaapp.plugin.modules.default.editor.Editor

        if (path.path != other.path.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path.path.hashCode()
    }


}