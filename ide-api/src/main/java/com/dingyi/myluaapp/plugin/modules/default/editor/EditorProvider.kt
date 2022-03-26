package com.dingyi.myluaapp.plugin.modules.default.editor

import com.dingyi.myluaapp.editor.language.textmate.TextMateLanguage
import com.dingyi.myluaapp.editor.language.textmate.TextMateLanguageProvider
import com.dingyi.myluaapp.plugin.api.context.PluginContext

import com.dingyi.myluaapp.plugin.api.editor.EditorProvider
import java.io.File

class EditorProvider(
    private val pluginContext: PluginContext
) : EditorProvider {

    private var id = 0

    override fun createEditor(editorPath: File): Editor {

        id++

        val editor =
            Editor(editorPath, id, pluginContext)

        val language = TextMateLanguageProvider.getTextMateLanguage(editorPath)

        if (language is TextMateLanguage) {
            editor.setColorScheme(language.getColorScheme())
            editor.setLanguage(language)
        }

        return editor
    }
}