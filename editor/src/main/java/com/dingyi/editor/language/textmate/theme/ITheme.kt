package com.dingyi.editor.language.textmate.theme

import com.dingyi.editor.language.textmate.bean.FontStyle

/**
 * @author: dingyi
 * @date: 2021/10/11 23:56
 * @description:
 **/

interface ITheme {
    fun match(scope: String): FontStyle?

    /**
     * return color index
     */
    fun parseColor(colorText:String):Int
    fun getDefaultColor():Int?
    fun init() //

    /**
     * get color by index
     */
    fun getColor(index:Int):Int
    fun matchSettings(settings: String):Int?
}


