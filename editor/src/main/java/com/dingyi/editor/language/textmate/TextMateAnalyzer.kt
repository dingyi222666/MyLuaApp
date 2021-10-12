package com.dingyi.editor.language.textmate

import io.github.rosemoe.sora.data.Span
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.model.LineTokens
import org.eclipse.tm4e.core.model.TMState

/**
 * @author: dingyi
 * @date: 2021/10/10 16:31
 * @description:
 **/
class TextMateAnalyzer(private val textMateBridgeLanguage: TextMateBridgeLanguage) :
    CodeAnalyzer {


    private var globalState = textMateBridgeLanguage.tokenizer.initialState


    override fun analyze(
        content: CharSequence,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        globalState = textMateBridgeLanguage.tokenizer.initialState

        var line = 0
        content.toString().reader().forEachLine { lineText ->
            if (line > 0) {
                result.addNormalIfNull()
            }
            if (!delegate.shouldAnalyze()) {
                return@forEachLine
            }
            val lineTokens = runCatching {
                tokenize(
                    lineText,
                    globalState,
                    0,
                    1000000000
                )
            }.getOrElse {
                tokenize(
                    lineText,
                    globalState,
                    0,
                    1000000000
                )
            }
            globalState = lineTokens.endState

            val theme = (textMateBridgeLanguage.codeEditor.colorScheme as TextMateScheme)
            lineTokens.tokens.forEach { token ->
                val fontStyle = theme.match(token)
                result.add(
                    line,
                    Span.obtain(
                        token.startIndex,
                        fontStyle?.foreground?.let { theme.parseColor(it) }
                            ?: theme.getDefaultColor() ?: EditorColorScheme.TEXT_NORMAL
                    )
                )
            }
            line++
        }
        result.determine(line)

    }

    private fun tokenize(
        lineText: String,
        globalState: TMState?,
        startIndex: Int,
        endIndex: Int
    ): LineTokens {
        return runCatching {
            textMateBridgeLanguage.tokenizer.tokenize(lineText, globalState, startIndex, endIndex)
        }.getOrElse {
            tokenize(lineText, globalState, startIndex, endIndex)
        }
    }


}