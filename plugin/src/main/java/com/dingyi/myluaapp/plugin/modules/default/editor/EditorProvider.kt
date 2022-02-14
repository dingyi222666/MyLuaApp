package com.dingyi.myluaapp.plugin.modules.default.editor

import android.content.Context
import com.dingyi.myluaapp.plugin.api.context.PluginContext

import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import java.io.File

class EditorProvider(
    private val pluginContext: PluginContext
) : EditorProvider {

    private var id = 0

    override fun createEditor(editorPath: File): com.dingyi.myluaapp.plugin.api.editor.Editor<*>? {
        val context = pluginContext
            .getActionService()
            .callAction<Context>(
                pluginContext.getActionService().createActionArgument()
                    .addArgument(editorPath),
                DefaultActionKey.CREATE_EDITOR_ACTION
            )

        id++

        return context?.let { Editor(it, editorPath, id) }
    }
}