package com.dingyi.myluaapp.editor.language.textmate

import android.util.Log
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.plugin.runtime.editor.EmptyLanguage


import java.io.File


object TextMateLanguageProvider {


    fun getTextMateLanguage(path: File): Language {
        val suffix = path.extension

        val textMateDir = File(Paths.resDir, "textmate/$suffix")

        if (!textMateDir.exists()) {
            return EmptyLanguage()
        }

        return kotlin.runCatching {
            TextMateLanguage.createLanguage(
                grammarFile = File(textMateDir, "$suffix.tmLanguage.json"),
                themeFile = File(Paths.resDir, "textmate/theme/default_theme.json"),
                languageName = suffix,
                languageConfigurationPath = File(textMateDir, "language-configuration.json"),
            )
        }.onFailure { exception ->
            //print exception use android.util.log
            Log.e("TextMateLanguageProvider", "getTextMateLanguage error", exception)
        }.getOrElse {
            EmptyLanguage()
        }

    }

}