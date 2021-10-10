package com.dingyi.editor.language.textmate

import android.graphics.Color
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.model.TMToken
import org.eclipse.tm4e.core.theme.css.CSSParser
import java.io.InputStream

/**
 * @author: dingyi
 * @date: 2021/10/10 17:21
 * @description:
 **/
class TextMateTheme(
    private val themeBlock: Builder.() -> Builder.Theme,
) : EditorColorScheme() {


    class Builder() {
        interface Theme {}
        class CSSTheme(val block: () -> InputStream) : Theme
        class TMTheme(val block: () -> InputStream) : Theme
        class VSCodeTheme(val block: () -> InputStream) : Theme
    }

    private var cssParser: CSSParser? = null

    private val theme = themeBlock.invoke(Builder())

    init {
        applyDefault()

        when (theme) {
            is Builder.CSSTheme -> {
                cssParser = CSSParser(theme.block.invoke())
            }
        }
    }

    fun match(token: TMToken): Int {
        return when (theme) {
            is Builder.CSSTheme -> {

                val style = cssParser?.getBestStyle(*token.type.split(".").toTypedArray())
                println("${token.type} $style")
                style?.color?.let {
                    val color = Color.rgb(it.red, it.green, it.blue)
                    setColor(color, color)
                    color
                } ?: TEXT_NORMAL
            }
            else -> 0
        }

    }

    /**
     * copy the them
     */
    fun clone(): TextMateTheme {
        val new = TextMateTheme(themeBlock)
        //TODO be implemented
        return new
    }


}