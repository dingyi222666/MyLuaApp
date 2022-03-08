package com.dingyi.myluaapp.editor.language.highlight.textmate.highlight

import com.dingyi.myluaapp.editor.language.highlight.IncrementLexerHighlightProvider
import com.dingyi.myluaapp.editor.language.highlight.textmate.language.TextMateLanguage
import io.github.rosemoe.sora.lang.styling.CodeBlock
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.textmate.core.grammar.StackElement
import io.github.rosemoe.sora.textmate.core.theme.IRawTheme
import java.io.File

class TextMateHighlightProvider(
    private val language: TextMateLanguage
): IncrementLexerHighlightProvider<TextMateHighlightProvider.TextMateState>() {


    class TextMateState(
        var ruleStack: StackElement
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TextMateState

            if (ruleStack != other.ruleStack) return false

            return true
        }

        override fun hashCode(): Int {
            return ruleStack.hashCode()
        }
    }

    override fun computeBlocks(
        text: CharSequence,
        styles: Styles,
        delegate: Delegate
    ): List<CodeBlock?>? {
        TODO("Not yet implemented")
    }

    override fun tokenizeLine(
        lineString: CharSequence,
        line: Int,
        tokenizeResult: LineTokenizeResult<TextMateState>?
    ): LineTokenizeResult<TextMateState> {
        TODO("Not yet implemented")
    }
}