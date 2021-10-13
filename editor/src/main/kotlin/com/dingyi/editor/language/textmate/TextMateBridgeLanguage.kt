package com.dingyi.editor.language.textmate

import com.dingyi.editor.language.BaseLanguage
import io.github.rosemoe.sora.interfaces.AutoCompleteProvider
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.interfaces.NewlineHandler
import io.github.rosemoe.sora.langs.internal.MyCharacter
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.SymbolPairMatch
import org.eclipse.tm4e.core.grammar.IGrammar
import org.eclipse.tm4e.core.model.Tokenizer
import org.eclipse.tm4e.core.registry.Registry
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


    var settings: Map<String, String>? = null

    var grammar: IGrammar

    init {
        val languageConfig = getLanguageConfig()
        grammar = registry.loadGrammarFromPathSync(
            languageConfig.languagePath,
            languageConfig.languageInputStream
        )

        settings = runCatching {
            TextMateGlobal.settings.filter {
                it["name"].toString() == grammar.name.toString()
            }[0] as Map<String, String>
        }.getOrNull().apply {
            println("this $this")
        }

    }

    data class LanguageConfig(
        val language: String,
        val languagePath: String,
        val languageInputStream: InputStream
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
        return SymbolPairMatch.DefaultSymbolPairs()
    }

}