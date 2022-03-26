package com.dingyi.myluaapp.plugin.modules.lua.editor

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.plugin.modules.lua.editor.format.LuaFormat
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaIncrementHighlightProvider
import com.dingyi.myluaapp.plugin.modules.lua.editor.highlight.LuaLexerHighlightProvider

class LuaLanguage(private val wrapperLanguage: Language) : Language() {

    override fun getName(): String {
        return "Lua Language"
    }

    override fun format(text: CharSequence?): CharSequence {
        return LuaFormat.format(text,tabSize)
    }


    override fun getHighlightProvider(): HighlightProvider {
        return wrapperLanguage.getHighlightProvider()
    }

    /**
    // The current  highlight provider is textmate, so don't need to override this method
    override fun getHighlightProvider(): HighlightProvider {
        return LuaIncrementHighlightProvider()
    }
    */
}