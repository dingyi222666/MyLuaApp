package com.dingyi.myluaapp.editor.language.textmate.theme

import android.graphics.Color
import android.util.ArrayMap
import android.util.SparseIntArray
import com.dingyi.myluaapp.editor.language.textmate.theme.ThemeRepository.fontStyleMap
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


    private val visualStudioCodeTheme = Theme.createFromRawTheme(visualStudioCodeRawTheme)

    //load all theme
    override fun init() {

    }

    override fun getColor(index: Int): Int? {
        return visualStudioCodeTheme
            .getColor(index)
            ?.let { color ->
                Color.parseColor(color)
            }
    }

    override fun getName(): String {
        return File(themePath).name.let { name -> name.substring(0, name.lastIndexOf('.')) }
    }


    override fun match(scope: Int): FontStyle? {
        return matchFontStyle(scope)
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


    override fun matchSettings(settings: String): Int? {
        return visualStudioCodeRawTheme
            .editorSettings
            .get(settings)
            .let { color -> Color.parseColor(color) }
    }

    override fun getThemeRaw(): io.github.rosemoe.sora.textmate.core.internal.theme.ThemeRaw {
        return visualStudioCodeRawTheme
    }

    override fun getDefaultColor(): Int? {
        return matchSettings("editor.foreground")?.plus(0xff)
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