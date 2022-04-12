package com.dingyi.myluaapp.common.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.dingyi.myluaapp.MainApplication
import kotlin.properties.Delegates

class ThemeManager {

    private var nowTheme = 0

    private val themeMap = mapOf(
        "default" to com.dingyi.myluaapp.R.style.Theme_MyLuaApp
    )

    var themeColors by Delegates.notNull<ThemeColors>()

    fun apply(context: AppCompatActivity) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val nowResKey = sharedPreferences.getString("theme", "default")

        val nowResId = themeMap.getOrDefault(nowResKey, com.dingyi.myluaapp.R.style.Theme_MyLuaApp)

        context.setTheme(nowResId)

        themeColors = ThemeColors(context)
    }

    fun getThemeId(): Int {
        return nowTheme
    }

    fun refreshTheme(): Int {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(MainApplication.instance)
        val nowResKey = sharedPreferences.getString("theme", "default")

        return themeMap.getOrDefault(nowResKey, com.dingyi.myluaapp.R.style.Theme_MyLuaApp)

    }


    @SuppressLint("ResourceType")
    inner class ThemeColors(activity: AppCompatActivity) {

        var colorBackground by Delegates.notNull<Int>()
        var colorPrimary by Delegates.notNull<Int>()

        //获取固定颜色
        val imageColorFilter =
            activity.resources.getColor(com.dingyi.myluaapp.R.color.theme_default_imageColorFilter,activity.theme)

        init {
            val typedArray = activity.obtainStyledAttributes(
                intArrayOf(
                    android.R.attr.colorPrimary,
                    com.dingyi.myluaapp.R.attr.theme_backgroundColor
                )
            )

            colorPrimary = typedArray.getColor(0, 0)
            colorBackground = typedArray.getColor(1, 0)

            typedArray.recycle()

        }
    }


}