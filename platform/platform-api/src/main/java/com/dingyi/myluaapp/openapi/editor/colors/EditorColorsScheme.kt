package com.dingyi.myluaapp.openapi.editor.colors

import android.graphics.Typeface


interface EditorColorsScheme {

    fun getColor(type:Int)

    fun setColor(type: Int,value:Int)

    fun setName(schemeName:String)

    fun getName(name:String)

    fun getFont():Typeface
}