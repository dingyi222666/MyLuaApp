package com.dingyi.myluaapp.plugin.modules.lua.editor

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaIncrementHighlightProvider
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaLexerHighlightProvider

class LuaLanguage: Language() {
    override fun getName(): String {
        return "Lua Language"
    }

    override fun getHighlightProvider(): HighlightProvider {
        return LuaIncrementHighlightProvider()
    }
}