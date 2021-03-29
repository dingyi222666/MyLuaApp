package com.dingyi.MyLuaApp.core.theme

import android.annotation.SuppressLint
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtil
import kotlin.properties.Delegates

class ThemeManager(activity: BaseActivity<*>) {
    private val themes = mutableMapOf<String, Int>()
    private val sharedPreferencesUtil = SharedPreferencesUtil("theme", activity)
    var nowThemeResourcesId: Int = 0;
    var colors: ThemeColors? = null;

    init {
        themes["默认"] = R.style.Theme_MyLuaApp
        themes["深蓝"] = R.style.Theme_MyLuaApp_Blue
        nowThemeResourcesId = themes[sharedPreferencesUtil.get("主题", "默认")]!!
        activity.setTheme(nowThemeResourcesId)
        colors = ThemeColors(activity);

    }

    @SuppressLint("ResourceType")
    inner class ThemeColors(activity: BaseActivity<*>) {

        var colorBackground by Delegates.notNull<Int>()
        var colorPrimary by Delegates.notNull<Int>()
        //获取固定颜色
        val imageColorFilter = activity.resources.getColor(R.color.theme_default_imageColorFilter)

        init {
            val typedArray = activity.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary, R.attr.theme_backgroundColor))

            colorPrimary = typedArray.getColor(0, 0)
            colorBackground = typedArray.getColor(1, 0)

            typedArray.recycle()

        }
    }

}