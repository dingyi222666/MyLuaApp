package com.dingyi.myluaapp.editor.language.textmate.theme

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

    fun init() //

    /**
     * get color by index
     */
    fun getColor(index: Int): Int?

    fun getThemeRaw(): ThemeRaw
    fun matchSettings(s: String): Int?
    fun getDefaultColor():Int?

}