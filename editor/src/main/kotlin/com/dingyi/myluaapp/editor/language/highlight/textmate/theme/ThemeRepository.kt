package com.dingyi.myluaapp.editor.language.highlight.textmate.theme

object ThemeRepository {

    val fontStyleMap = mapOf(
        -1 to "notset",
        0 to "none",
        1 to "italic",
        2 to "Bold",
        3 to "underline"
    )

    private val allTheme = mutableListOf<ITheme>()


    fun registerTheme(theme:ITheme) {
        allTheme.add(theme)
    }

    fun getTheme(name:String):ITheme? {
        return allTheme.find {
            it.getName() == name
        }
    }

}