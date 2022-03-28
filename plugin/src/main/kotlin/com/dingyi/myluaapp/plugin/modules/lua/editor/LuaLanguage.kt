package com.dingyi.myluaapp.plugin.modules.lua.editor

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import com.dingyi.myluaapp.editor.language.WrapperLanguage
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.modules.lua.editor.format.LuaFormat
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaIncrementHighlightProvider
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaLexerHighlightProvider

class LuaLanguage(editor: Editor) : WrapperLanguage(editor.getLanguage(),editor) {

    override fun getName(): String {
        return "Lua Language"
    }

    override fun format(text: CharSequence?): CharSequence {
        return LuaFormat.format(text,tabSize)
    }



}