package com.dingyi.editor.language.textmate

import com.dingyi.editor.language.BaseLanguage
import io.github.rosemoe.sora.interfaces.AutoCompleteProvider
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.interfaces.NewlineHandler
import io.github.rosemoe.sora.langs.internal.MyCharacter
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.SymbolPairMatch
import org.eclipse.tm4e.core.grammar.IGrammar
import org.eclipse.tm4e.core.registry.Registry
import org.eclipse.tm4e.languageconfiguration.internal.LanguageConfiguration
import java.io.InputStream

/**
 * @author: dingyi
 * @date: 2021/10/10 15:14
 * @description:The TextMate Bridge Class
 **/
abstract class TextMateBridgeLanguage(
    val codeEditor: CodeEditor
) : BaseLanguage() {

    val registry = Registry()


    override fun getAnalyzer(): CodeAnalyzer {
        return TextMateAnalyzer(this)
    }

    abstract fun getLanguageConfig(): LanguageConfig


    val grammar: IGrammar

    private val languageConfiguration: LanguageConfiguration?

    init {
        val languageConfig:LanguageConfig by lazy(LazyThreadSafetyMode.NONE) { getLanguageConfig() }

        grammar = registry.loadGrammarFromPathSync(
            languageConfig.languagePath,
            languageConfig.languageGrammarInputStream
        )

        languageConfiguration = languageConfig.languageConfigurationInputStream?.reader().use {
            LanguageConfiguration.load(it)
        }
        
    }

    data class LanguageConfig(
        val language: String,
        val languagePath: String,
        val languageGrammarInputStream: InputStream,
        val languageConfigurationInputStream: InputStream? = null
    )

    override fun isAutoCompleteChar(ch: Char): Boolean {
        return MyCharacter.isJavaIdentifierPart(ch.code)
    }

    override fun format(text: CharSequence): CharSequence {
        return text
    }

    override fun useTab(): Boolean {
        return false
    }

    override fun getAutoCompleteProvider(): AutoCompleteProvider {
        return AutoCompleteProvider { _, _, _, _ -> mutableListOf() }
    }

    override fun getIndentAdvance(content: String?): Int {
        return 0
    }

    override fun getNewlineHandlers(): Array<NewlineHandler> {
        return arrayOf()
    }

    override fun getSymbolPairs(): SymbolPairMatch {
        return languageConfiguration?.autoClosingPairs?.let {
            SymbolPairMatch().apply {
                it.forEach {
                    this.putPair(
                        it.key.toCharArray()[0],
                        SymbolPairMatch.Replacement(it.key + it.value, it.key.length)
                    )
                }
            }
        } ?: SymbolPairMatch.DefaultSymbolPairs()
    }

}