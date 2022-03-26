package com.dingyi.myluaapp.plugin.modules.default.editor

import android.view.View
import android.view.ViewGroup
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import io.github.rosemoe.sora.widget.CodeEditor

import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorListener


import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import com.dingyi.myluaapp.plugin.runtime.editor.EmptyLanguage
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.EventReceiver
import io.github.rosemoe.sora.event.Unsubscribe
import io.github.rosemoe.sora.widget.schemes.*
import io.github.rosemoe.sorakt.subscribeEvent

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.lang.ref.WeakReference

class Editor(
    private val path: File,
    private val id: Int,
    private val pluginContext: PluginContext
) : Editor, EventReceiver<ContentChangeEvent> {

    private val allEditorListener = mutableListOf<EditorListener>()
    private var currentColorScheme: EditorColorScheme = SchemeEclipse()
    private var currentLanguage: Language = EmptyLanguage()

    private var currentEditor = WeakReference<CodeEditor>(null)

    private var currentEditorState = EditorState(path.path, 0, 0, 0, 0, 0f)

    override fun getText(): CharSequence? {
        return currentEditor.get()?.text
    }

    override fun getCurrentLine(): Int {
        return currentEditor.get()?.text?.cursor?.leftLine ?: 0
    }

    override fun getCurrentColumn(): Int {
        return currentEditor.get()?.text?.cursor?.leftColumn ?: 0
    }


    override fun setColorScheme(scheme: EditorColorScheme) {
        this.currentColorScheme = scheme
        if (currentEditor.get() != null) {
            currentEditor.get()?.colorScheme = scheme
        }
    }

    override fun getColorScheme(): EditorColorScheme {
        return currentColorScheme
    }


    override fun setText(charSequence: CharSequence) {
        currentEditor.get()?.setText(charSequence)
    }

    override fun appendText(charSequence: CharSequence) {
        currentEditor.get()?.let {
            it.text?.beginBatchEdit()
            it.text.insert(getCurrentLine(), getCurrentColumn(), charSequence)
            it.text.endBatchEdit()
        }
    }


    override fun getId(): Int {
        return id
    }



    override fun saveState(): EditorState {

        if (currentEditor.get() != null) {
            currentEditor.get()?.let {
                currentEditorState = EditorState(
                    path = path.path,
                    line = getCurrentLine(),
                    column = getCurrentColumn(),
                    scrollX = it.scroller.currX,
                    scrollY = it.scroller.currY,
                    textSize = it.textSizePx
                )
            }
        } else {
            currentEditorState = EditorState(
                path = path.path,
                0, 0, 0, 0, 0f
            )
        }

        return currentEditorState
    }

    override fun restoreState(editorState: EditorState) {
        currentEditorState = editorState
    }

    private fun doRestoreState(editorState: EditorState) {
        currentEditor.get()?.apply {
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

    override fun isModified(): Boolean {
        if (currentEditor.get() == null) {
            return false
        }
        return currentEditor.get()?.text.toString() == path.readText()
    }

    override fun getLanguage(): Language {
        return currentLanguage
    }

    override fun setLanguage(language: Language) {
        currentLanguage = language
        if (currentEditor.get() != null) {
            currentEditor.get()?.setEditorLanguage(currentLanguage)
        }
    }

    override fun addEditorListener(listener: EditorListener) {
        allEditorListener.add(listener)
    }

    override fun removeEditorListener(listener: EditorListener) {
        allEditorListener.remove(listener)
    }

    override fun getCurrentView(): View? {
        return currentEditor.get()
    }

    override fun undo() {
        if (currentEditor.get()?.canUndo() == true) {
            currentEditor.get()?.undo()
        }
    }

    override fun redo() {
        if (currentEditor.get()?.canRedo() == true) {
            currentEditor.get()?.redo()
        }
    }

    override fun close() {
        currentEditor.clear()
        allEditorListener.forEach { it.onEditorClose() }
        allEditorListener.clear()
    }

    override fun format() {
        currentEditor.get()
            ?.formatCodeAsync()

    }

    override fun save() {

        if (currentEditor.get() == null) {
            return
        }

        if (!path.isFile) {
            pluginContext.getEditorService().closeEditor(this)
            throw FileNotFoundException("The File was deleted.")
        }

        currentEditor.get()?.let {
            path.writeText(it.text.toString())
        }

        allEditorListener.forEach { it.onEditorSave() }
    }

    override fun binCurrentView(r: CodeEditor) {
        if (currentEditor.get() != null) {
            save()
            //TODO unsubscribe event
        }
        currentEditor = WeakReference(r)
        r.subscribeEvent(this)
        r.setEditorLanguage(currentLanguage)
        r.colorScheme = currentColorScheme
    }

    override suspend fun read():Unit = withContext(Dispatchers.IO) {
        if (currentEditor.get() == null) {
            return@withContext
        }
        if (!path.isFile) {
            pluginContext.getEditorService().closeEditor(this@Editor)
            throw FileNotFoundException("The File was deleted.")
        }
        val text = path.readText()
        withContext(Dispatchers.Main) {
            setText(text)
            doRestoreState(currentEditorState)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as com.dingyi.myluaapp.plugin.modules.default.editor.Editor

        if (path.absolutePath == other.path.absolutePath) return true

        return false
    }

    override fun hashCode(): Int {
        return path.absolutePath.hashCode()
    }

    override fun onReceive(event: ContentChangeEvent, unsubscribe: Unsubscribe) {
        allEditorListener.forEach {
            it.onEditorChange(this, event)
        }
    }


}