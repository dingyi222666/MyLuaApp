package com.dingyi.myluaapp.editor.language.textmate.grammar

import com.dingyi.myluaapp.editor.language.textmate.theme.ITheme
import io.github.rosemoe.sora.textmate.core.grammar.IGrammar
import io.github.rosemoe.sora.textmate.core.registry.Registry
import java.io.File

object GrammarRepository {

    private val registry = Registry()

    private val grammarMap = mutableMapOf<String, IGrammar>()

    fun loadGrammar(grammarPath: File): IGrammar {
        val tmp = grammarMap.get(grammarPath.path)
        if (tmp != null) {
            return tmp
        }
        val result = registry.loadGrammarFromPathSync(grammarPath)
        grammarMap[grammarPath.path] = result
        return result
    }

    fun getGrammar(name: String): IGrammar {
        return registry.grammarForScopeName("source.$name")
    }

    fun setTheme(theme: ITheme) {
        registry.setTheme(theme.getThemeRaw())
    }

}