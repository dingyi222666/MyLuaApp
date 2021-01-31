package com.dingyi.MyLuaApp.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import com.dingyi.MyLuaApp.R

@SuppressLint("ResourceType")
class ThemeUtil(private val context: Activity) {
    val backgroundColor: Int
    val colorPrimary: Int;
    val imageColorFilter: Int;
    val colorBackgroundColor: Int;
    val textColor:Int;
    val rippleColor:Int;

    private var rippleDrawableBorderless :RippleDrawable;
    init {
        val array = context.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary,android.R.attr.colorBackground,
                R.attr.theme_textColor,android.R.attr.selectableItemBackgroundBorderless,R.attr.theme_rippleColor,
        R.attr.theme_backgroundColor))
        colorPrimary = array.getColor(0, 0)
        colorBackgroundColor=array.getColor(1,0)
        textColor=array.getColor(2,0);
        rippleDrawableBorderless= context.resources.getDrawable(array.getResourceId(3,0),context.theme) as RippleDrawable
        rippleColor=array.getColor(4,0)
        backgroundColor=array.getColor(5,0)

        //获取固定颜色
        imageColorFilter = context.resources.getColor(R.color.theme_default_imageColorFilter)
        array.recycle()
    }

    fun getRippleDrawable():RippleDrawable {
        rippleDrawableBorderless.setColor(ColorStateList(arrayOf(intArrayOf()), intArrayOf(rippleColor)))
        return rippleDrawableBorderless
    }
}