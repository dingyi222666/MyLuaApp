package com.dingyi.editor.language.textmate

import android.graphics.Color
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.theme.css.CSSParser
import java.io.InputStream

/**
 * @author: dingyi
 * @date: 2021/10/10 17:21
 * @description:
 **/
class BaseTextMateTheme(
    private val cssInputStream: () -> InputStream
) : EditorColorScheme() {

    private val cssParser = CSSParser(cssInputStream())


    init {
        applyDefault()
    }

    fun match(type: String): Int {
        val style = cssParser.getBestStyle(*type.split(".").toTypedArray())
        return style?.color?.let {
            val color = Color.rgb(it.red, it.green, it.blue)
            setColor(color, color)
            color
        } ?: this.getColor(TEXT_NORMAL)
    }

    fun copy():BaseTextMateTheme {
        val new = BaseTextMateTheme(cssInputStream)
        //TODO be implemented
        return new
    }


}