package com.dingyi.editor.language.textmate.theme

import android.graphics.Color
import android.util.ArrayMap
import android.util.SparseIntArray
import com.dingyi.editor.language.textmate.bean.FontStyle
import com.dingyi.editor.language.textmate.bean.VSCodeThemeBean
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @author: dingyi
 * @date: 2021/10/11 23:58
 * @description:
 **/
class VSCodeTheme(val path: String, val block: () -> InputStream) : ITheme {

    private val vsCodeTheme =
        Gson().fromJson(InputStreamReader(block()), VSCodeThemeBean::class.java)

    private val scopesThemeMap = ArrayMap<String, FontStyle>(100)

    private val colorCacheMap = ArrayMap<String, Int>()


    private val colorCacheList = SparseIntArray()


    //load all theme
    override fun init() {

        vsCodeTheme.editorColors?.forEach { editorColor ->
            val color = Color.parseColor(editorColor.value.run {
                if (this.length < 9) {
                    "#" + ((this.length - 1..7).joinToString(
                        separator = ""
                    ) { "F" }) + this.substring(1)
                } else this
            })
            colorCacheMap[editorColor.key] = colorCacheList.size()
            colorCacheList.append(colorCacheList.size(), color)
        }
    }

    override fun getColor(index: Int): Int {
        return colorCacheList[index]
    }


    override fun match(scope: String): FontStyle? {
        return scopesThemeMap[scope] ?: matchFontStyle(scope)
    }

    private fun matchFontStyle(scope: String): FontStyle? {
        vsCodeTheme.tokenColors.forEach { tokenColor ->
            if (tokenColor.scope is String && tokenColor.scope == scope) {
                scopesThemeMap[tokenColor.scope as String] = tokenColor.style
                return tokenColor.style
            } else if (tokenColor.scope is List<*>) {
                (tokenColor.scope as List<String>).forEach {
                    if (it == scope) {
                        scopesThemeMap[it] = tokenColor.style
                        return tokenColor.style
                    }
                }
            }
        }
        return null
    }

    override fun parseColor(colorText: String): Int {
        return if (colorCacheMap.containsKey(colorText)) {
            colorCacheMap.getValue(colorText)
        } else {
            val color = Color.parseColor(colorText)
            val index = colorCacheList.size()
            colorCacheMap[colorText] = index
            colorCacheList.put(index, color)
            index
        }.run {
            this + 0xff
        }
    }


    override fun getDefaultColor(): Int? {
        return colorCacheMap["editor.foreground"]?.plus(0xff)
    }

    override fun matchSettings(settings: String): Int? {
        return colorCacheMap[settings]?.run {
            colorCacheList[this]
        }
    }

}
