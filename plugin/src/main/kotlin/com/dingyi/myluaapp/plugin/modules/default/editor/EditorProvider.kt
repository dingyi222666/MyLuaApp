package com.dingyi.myluaapp.plugin.modules.default.editor

import android.content.Context
import com.dingyi.myluaapp.plugin.api.context.PluginContext

import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import io.github.rosemoe.sora.widget.CodeEditor
import java.io.File

class EditorProvider(
    private val pluginContext: PluginContext
) : EditorProvider {

    private var id = 0

    override fun createEditor(editorPath: File): Editor {

        id++

        return Editor(editorPath, id)
    }
}