package com.dingyi.editor.language.textmate.theme

import org.eclipse.tm4e.core.internal.theme.ThemeRaw
import org.eclipse.tm4e.core.theme.IRawThemeSetting

/**
 * @author: dingyi
 * @date: 2021/10/12 14:29
 * @description:
 **/
class ThemeRaw() : ThemeRaw() {


    override fun getSettings(): MutableCollection<IRawThemeSetting> {
        val superSettings = super.getSettings()
        if (superSettings != null) {
            return superSettings
        }
        return this["tokenColors"] as MutableCollection<IRawThemeSetting>
    }

    val editorSettings: Map<String, String>
        get() = this["colors"] as Map<String, String>

}