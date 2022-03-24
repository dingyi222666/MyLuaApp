package com.dingyi.myluaapp.editor.language.textmate.theme

import android.graphics.Color
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

class TextMateColorScheme(
    private val theme: ITheme?
) : EditorColorScheme() {

    init {

        theme?.init()
        applyDefault()

        setColor(LINE_DIVIDER, Color.TRANSPARENT)


        theme?.matchSettings("editor.background")?.let { color ->
            setColor(WHOLE_BACKGROUND, color)
            setColor(LINE_NUMBER_BACKGROUND, color)
        }
        theme?.matchSettings("editor.foreground")?.let {
            setColor(LINE_NUMBER, it)

        }

        theme?.matchSettings("editor.selectionBackground")?.let {
            setColor(SELECTED_TEXT_BACKGROUND,it)
            setColor(SELECTION_INSERT,it)
        }

        theme?.matchSettings("editorLineNumber.foreground")?.let {
            setColor(LINE_NUMBER,it)
        }

        theme?.matchSettings("editorGutter.background")?.let {
            setColor(LINE_NUMBER_PANEL,it)
        }

        theme?.matchSettings("editor.lineHighlightBackground")?.let {
            setColor(CURRENT_LINE,it)
        }



        val blockLineColor =
            (getColor(WHOLE_BACKGROUND) + getColor(TEXT_NORMAL)) / 2 and 0x00FFFFFF or -0x78000000
        setColor(BLOCK_LINE, blockLineColor)
        val blockLineColorCur = blockLineColor or -0x1000000
        setColor(BLOCK_LINE_CURRENT, blockLineColorCur)


        theme?.matchSettings("editorIndentGuide.background")?.let {
            setColor(BLOCK_LINE,it)
        }

        theme?.matchSettings("editorIndentGuide.activeBackground")?.let {
            setColor(BLOCK_LINE_CURRENT,it)
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