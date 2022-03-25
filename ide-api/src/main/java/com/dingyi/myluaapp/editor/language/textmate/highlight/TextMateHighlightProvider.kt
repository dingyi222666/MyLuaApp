package com.dingyi.myluaapp.editor.language.textmate.highlight

import com.dingyi.myluaapp.editor.highlight.IncrementStateHighlightProvider
import com.dingyi.myluaapp.editor.language.textmate.TextMateLanguage
import com.dingyi.myluaapp.editor.language.textmate.fold.IndentRange
import io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager
import io.github.rosemoe.sora.lang.styling.CodeBlock
import io.github.rosemoe.sora.lang.styling.Span
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.lang.styling.TextStyle
import io.github.rosemoe.sora.langs.textmate.analyzer.TextMateAnalyzer

import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.textmate.core.grammar.ITokenizeLineResult2
import io.github.rosemoe.sora.textmate.core.grammar.StackElement
import io.github.rosemoe.sora.textmate.core.internal.grammar.StackElementMetadata
import io.github.rosemoe.sora.textmate.core.theme.FontStyle
import io.github.rosemoe.sora.textmate.languageconfiguration.internal.supports.Folding
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.util.*

class TextMateHighlightProvider(
    private val language: TextMateLanguage
) : IncrementStateHighlightProvider<TextMateHighlightProvider.TextMateState>() {


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
        text: Content,
        styles: Styles,
        delegate: Delegate
    ): List<CodeBlock> {

        val blocks = mutableListOf<CodeBlock>()

        if (language.getLanguageConfiguration() == null) {
            return blocks
        }

        val folding = language.getLanguageConfiguration()?.folding ?: return blocks
        kotlin.runCatching {
            val foldingRegions = IndentRange.computeRanges(
                text,
                language.tabSize,
                folding.offSide,
                folding,
                TextMateAnalyzer.MAX_FOLDING_REGIONS_FOR_INDENT_LIMIT,
                delegate
            )

            var i = 0
            while (i < foldingRegions.length() && !delegate.isCancelled()) {
                val startLine = foldingRegions.getStartLineNumber(i)
                val endLine = foldingRegions.getEndLineNumber(i)
                if (startLine != endLine) {
                    val codeBlock = CodeBlock()
                    codeBlock.toBottomOfEndLine = true
                    codeBlock.startLine = startLine
                    codeBlock.endLine = endLine

                    // It's safe here to use raw data because the Content is only held by this thread
                    val length = text.getColumnCount(startLine)
                    val chars = text.getLine(startLine).rawData
                    codeBlock.startColumn =
                        IndentRange.computeStartColumn(chars, length, language.tabSize)
                    codeBlock.endColumn = codeBlock.startColumn
                    blocks.add(codeBlock)
                }
                i++
            }
            Collections.sort(blocks, CodeBlock.COMPARATOR_END)
        }
        return blocks
    }


    override fun tokenizeLine(
        lineString: CharSequence,
        line: Int,
        tokenizeResult: LineTokenizeResult<TextMateState>?
    ): LineTokenizeResult<TextMateState> {
        val data = tokenizeResult?.data?.ruleStack ?: StackElement.NULL
        val lineText: String = lineString.toString()
        val tokens = mutableListOf<Span>()
        val lineTokens: ITokenizeLineResult2 =
            language.getGrammar()
                .tokenizeLine2(lineText, data)
        val tokensLength = lineTokens.tokens.size / 2
        for (i in 0 until tokensLength) {
            val startIndex = lineTokens.tokens[2 * i]
            if (i == 0 && startIndex != 0) {
                tokens.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
            }
            val metadata = lineTokens.tokens[2 * i + 1]
            val foreground = StackElementMetadata.getForeground(metadata)
            val fontStyle = StackElementMetadata.getFontStyle(metadata)
            val span = Span.obtain(
                startIndex,
                TextStyle.makeStyle(
                    foreground + 255,
                    0,
                    fontStyle and FontStyle.Bold != 0,
                    fontStyle and FontStyle.Italic != 0,
                    false
                )
            )
            tokens.add(span)
        }
        return LineTokenizeResult(
            data = TextMateState(
                lineTokens.ruleStack
            ),
            spans = tokens
        )
    }
}