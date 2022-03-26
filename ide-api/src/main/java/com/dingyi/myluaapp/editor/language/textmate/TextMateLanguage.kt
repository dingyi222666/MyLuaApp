package com.dingyi.myluaapp.editor.language.textmate

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.editor.language.textmate.grammar.GrammarRepository
import com.dingyi.myluaapp.editor.language.textmate.highlight.TextMateHighlightProvider
import com.dingyi.myluaapp.editor.language.textmate.theme.ITheme
import com.dingyi.myluaapp.editor.language.textmate.theme.TextMateColorScheme
import com.dingyi.myluaapp.editor.language.textmate.theme.ThemeRepository
import io.github.rosemoe.sora.textmate.core.grammar.IGrammar
import io.github.rosemoe.sora.textmate.languageconfiguration.ILanguageConfiguration
import io.github.rosemoe.sora.textmate.languageconfiguration.internal.LanguageConfiguration
import java.io.File

class TextMateLanguage(
    private val grammar: IGrammar,
    private val theme: ITheme,
    private val languageConfiguration: ILanguageConfiguration?,
    private val languageName: String
) : Language() {

    override fun getName(): String {
        return "TextMate"
    }

    override fun getHighlightProvider(): HighlightProvider {
        return TextMateHighlightProvider(this)
    }

    private var defaultColorScheme: TextMateColorScheme? = null

    fun getGrammar() = grammar

    fun getTheme() = theme

    fun getLanguageConfiguration(): ILanguageConfiguration? = languageConfiguration

    fun getColorScheme(): TextMateColorScheme {
        return (defaultColorScheme ?: TextMateColorScheme(theme))?.apply {
            defaultColorScheme = this
        }
    }




    override fun destroy() {
        currentHighlightProvider?.destroy()
    }


    companion object {
        fun createLanguage(
            grammarFile: File,
            themeFile: File,
            languageConfigurationPath: File,
            languageName: String
        ): TextMateLanguage {
            val grammar = GrammarRepository
                .loadGrammar(grammarFile)

            val theme = ThemeRepository
                .loadTheme(themeFile)


            val languageConfiguration = kotlin.runCatching {
                LanguageConfiguration.load(
                    languageConfigurationPath.reader()
                )
            }.getOrNull()

            return TextMateLanguage(grammar, theme, languageConfiguration, languageName)
        }

    }


}