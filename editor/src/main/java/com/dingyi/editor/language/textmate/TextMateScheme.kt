package com.dingyi.editor.language.textmate

import com.dingyi.editor.language.textmate.bean.FontStyle
import com.dingyi.editor.language.textmate.theme.ITheme
import com.dingyi.editor.language.textmate.theme.VSCodeTheme
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.model.TMToken

/**
 * @author: dingyi
 * @date: 2021/10/10 17:21
 **/
class TextMateScheme(
    themeBlock: () -> ITheme,
) : EditorColorScheme() {


    val theme = themeBlock()

    init {
        theme.init()
        applyDefault()
        when (theme) {
            is VSCodeTheme -> {
                theme.matchSettings("editor.background")?.let { color ->
                    setColor(WHOLE_BACKGROUND, color)
                    setColor(LINE_NUMBER_BACKGROUND, color)
                }
                theme.matchSettings("editor.foreground")?.let {
                    setColor(LINE_NUMBER, it)
                    setColor(BLOCK_LINE,it-0x2f000000)
                    setColor(BLOCK_LINE_CURRENT,it)
                }

            }
        }
    }


    override fun getColor(type: Int): Int {
        return if (type < 0xff) {
            super.getColor(type)
        } else {
            theme.getColor(type - 0xff)
        }
    }


    fun match(token: TMToken): FontStyle? {
        return when (theme) {
            is VSCodeTheme -> matchVSCodeTheme(token)
            else -> null
        }
    }


    private fun matchVSCodeTheme(token: TMToken): FontStyle? {

        val scopes = token.scopes

        scopes?.forEach {
            val cachedResult = theme.match(it)
            if (cachedResult != null) {
                return cachedResult
            }
        }

        return null

    }

    fun parseColor(colorString: String): Int {
        return theme.parseColor(colorString)
    }

    fun getDefaultColor(): Int? {
        return theme.getDefaultColor()
    }

}