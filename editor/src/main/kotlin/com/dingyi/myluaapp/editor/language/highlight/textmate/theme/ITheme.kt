package com.dingyi.myluaapp.editor.language.highlight.textmate.theme

import io.github.rosemoe.sora.textmate.core.internal.theme.ThemeRaw


/**
 * @author: dingyi
 * @date: 2021/10/11 23:56
 * @description:
 **/

interface ITheme {


    fun getName():String

    fun match(metadata: Int): FontStyle?

    /**
     * return color index
     */
    fun parseColor(colorText: String): Int
    fun init() //

    /**
     * get color by index
     */
    fun getColor(index: Int): Int
    fun matchSettings(settings: String): Int?
    fun getThemeRaw(): ThemeRaw

}