package com.dingyi.myluaapp.editor.language.highlight.textmate.theme

import android.graphics.Color
import android.util.ArrayMap
import android.util.SparseIntArray
import com.dingyi.myluaapp.editor.language.highlight.textmate.theme.ThemeRepository.fontStyleMap
import io.github.rosemoe.sora.textmate.core.internal.grammar.StackElementMetadata

import io.github.rosemoe.sora.textmate.core.internal.theme.reader.ThemeReader
import io.github.rosemoe.sora.textmate.core.theme.Theme
import java.io.File
import java.io.FileInputStream

class VisualStudioCodeTheme(
    private val themePath: String
) : ITheme {

    private val visualStudioCodeRawTheme =
        ThemeRaw().apply {
            putAll(
                ThemeReader.readThemeSync(
                    themePath,
                    FileInputStream(themePath)
                ) as Map<String, Any>
            )
        }

    private val scopesThemeMap = ArrayMap<Int, FontStyle>(100)

    private val colorCacheMap = ArrayMap<String, Int>()


    private val colorCacheList = SparseIntArray()

    private val visualStudioCodeTheme = Theme.createFromRawTheme(visualStudioCodeRawTheme)

    //load all theme
    override fun init() {

        visualStudioCodeRawTheme.editorSettings.forEach { editorColor ->
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

    override fun getName(): String {
        return File(themePath).name.let { name -> name.substring(0, name.lastIndexOf('.')) }
    }



    override fun match(scope: Int): FontStyle? {
        return scopesThemeMap[scope] ?: matchFontStyle(scope)
    }

    private fun matchFontStyle(scope: Int): FontStyle? {
        val foreground = StackElementMetadata.getForeground(scope)
        if (visualStudioCodeTheme.getColor(foreground) != "#000000") {
            return FontStyle(
                visualStudioCodeTheme.getColor(foreground),
                fontStyleMap[StackElementMetadata.getFontStyle(scope)],
                visualStudioCodeTheme.getColor(StackElementMetadata.getBackground(scope))
            )
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

    override fun matchSettings(settings: String): Int? {
        return colorCacheMap[settings]?.run {
            colorCacheList[this]
        }
    }

    override fun getThemeRaw():io.github.rosemoe.sora.textmate.core.internal.theme.ThemeRaw {
        return visualStudioCodeRawTheme
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VisualStudioCodeTheme

        if (themePath != other.themePath) return false

        return true
    }

    override fun hashCode(): Int {
        return themePath.hashCode()
    }

}