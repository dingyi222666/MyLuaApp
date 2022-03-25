package com.dingyi.myluaapp.editor.language.textmate.theme

import com.dingyi.myluaapp.editor.language.textmate.grammar.GrammarRepository
import io.github.rosemoe.sora.textmate.core.grammar.IGrammar
import java.io.File

object ThemeRepository {

    val fontStyleMap = mapOf(
        -1 to "notset",
        0 to "none",
        1 to "italic",
        2 to "Bold",
        3 to "underline"
    )

    private val allTheme = mutableListOf<ITheme>()

    private val themeMap = mutableMapOf<String, ITheme>()


    fun registerTheme(theme: ITheme) {
        allTheme.add(theme)
        GrammarRepository.setTheme(theme)
    }

    fun loadTheme(themePath: File): ITheme {
        if (themeMap[themePath.path] != null) {
            return themeMap.getValue(themePath.path)
        }
        val theme = VisualStudioCodeTheme(themePath.path)
        registerTheme(theme)
        themeMap[themePath.path] = theme
        return theme
    }

    fun getTheme(name: String): ITheme? {
        return allTheme.find {
            it.getName() == name
        }
    }

}