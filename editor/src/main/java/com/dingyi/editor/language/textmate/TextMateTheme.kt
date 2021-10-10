package com.dingyi.editor.language.textmate

import android.graphics.Color
import android.util.ArrayMap
import com.dingyi.editor.language.textmate.bean.ThemeBean
import com.google.gson.Gson
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.model.TMToken
import java.io.InputStream
import java.io.InputStreamReader

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
        class TMTheme(val path: String, val block: () -> InputStream) : Theme
        class VSCodeTheme(val path: String, val block: () -> InputStream) : Theme
    }

    private var vsCodeTheme: ThemeBean? = null
    private val theme = themeBlock.invoke(Builder())

    private val colorCache = ArrayMap<String, Int>()



    init {
        applyDefault()
        when (theme) {
            is Builder.VSCodeTheme -> {
                vsCodeTheme =
                    Gson().fromJson(InputStreamReader(theme.block()), ThemeBean::class.java)

                vsCodeTheme?.colors?.forEach {

                    when (it.key) {
                        "editor.foreground" -> {
                            setColor(LINE_NUMBER,Color.parseColor(it.value))
                        }
                        "editor.background" -> {

                            setColor(LINE_NUMBER_BACKGROUND,Color.parseColor(it.value))
                            setColor(WHOLE_BACKGROUND,Color.parseColor(it.value))
                        }
                    }
                }

            }
        }
    }

    private fun <T> getOrNull(t: T?): T {
        return t!!
    }

    private fun matchCache(name: String): Int? {
        if (colorCache[name] != null) {
            val color = getOrNull(colorCache[name])
            setColor(color, color)
            return color
        }
        return null
    }

    fun match(token: TMToken): Int? {
        val scopes = token.scopes.toMutableList()
        scopes.removeAt(0)
        for (index in scopes.lastIndex downTo 0) {

            val scope = scopes[index]
            //if cached

            val cachedResult = matchCache(scope)
            if (cachedResult != null) {
                return cachedResult
            }

            var color = matchScope(scope, scope)

            if (color != null) {
                return color
            }

            val splitScope = scope.split(".").toMutableList()

            //逐个去匹配

            while (splitScope.isNotEmpty()) {
                val tmpString = splitScope.joinToString(separator = ".")

                color = matchScope(tmpString, scope)
                println("color=$color scope=$tmpString")
                if (color != null) {
                    return color
                }

                splitScope.removeAt(splitScope.lastIndex)

            }

        }

        return TEXT_NORMAL

    }

    private fun putCache(color: Int, scope: String) {
        setColor(color, color)
        colorCache[scope] = color
    }

    private fun matchScope(matchScope: String, scope: String): Int? {
        return when (theme) {
            is Builder.VSCodeTheme -> {
                vsCodeTheme?.tokenColors.run {
                    var result: String? = null
                    this?.forEach { tokenColor ->
                        if (tokenColor.scope is String) {
                            if (matchScope == tokenColor.scope) {
                                result = tokenColor.settings.foreground
                            }
                        } else if (tokenColor.scope is List<*>) {
                            (tokenColor.scope as List<*>).forEach {
                                if (it.toString() == matchScope) {
                                    result = tokenColor.settings.foreground
                                    return@forEach
                                }
                            }
                        }
                    }
                    result
                }?.run {
                    Color.parseColor(this)
                }
            }
            else -> null
        }?.apply {
            putCache(this, scope)
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