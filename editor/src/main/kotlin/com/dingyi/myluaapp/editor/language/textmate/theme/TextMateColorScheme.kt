package com.dingyi.myluaapp.editor.language.textmate.theme

import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

class TextMateColorScheme(
    private val theme: ITheme?
) : EditorColorScheme() {

    init {

        theme?.init()
        applyDefault()

        theme?.matchSettings("editor.background")?.let { color ->
            setColor(WHOLE_BACKGROUND, color)
            setColor(LINE_NUMBER_BACKGROUND, color)
        }
        theme?.matchSettings("editor.foreground")?.let {
            setColor(LINE_NUMBER, it)
            setColor(BLOCK_LINE, it - 0x2f000000)
            setColor(BLOCK_LINE_CURRENT, it)
        }


    }


    override fun getColor(type: Int): Int {
        var type = type
        if (type >= 255) {
            type -= 255
            if (theme != null) {
                return theme.getColor(type) ?: theme.getDefaultColor() ?: super.getColor(
                    TEXT_NORMAL
                )
            }
        }

        return super.getColor(type)
    }
}