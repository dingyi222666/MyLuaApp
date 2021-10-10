package com.dingyi.editor.language.textmate

import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.model.TMState

/**
 * @author: dingyi
 * @date: 2021/10/10 16:31
 * @description:
 **/
class TextMateAnalyzer(private val textMateBridgeLanguage: TextMateBridgeLanguage) : CodeAnalyzer {


    private val bufferSpanMap = mutableMapOf<Int,
            MutableList<Pair<Int, Int>>>()


    private val lineContent = LineContent()

    override fun analyze(
        content: CharSequence,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        lineContent.commitText(content.toString())

        var state = textMateBridgeLanguage.tokenizer.initialState
        var lines = 0
        lineContent.getLines().forEachIndexed { index, charArray ->
            lines = index
            if (!delegate.shouldAnalyze()) {
                return
            }
            if (lineContent.getDiffLines().containsKey(index)) {
                state = scanDiffLine(index, charArray, result, delegate, state)
            } else {
                putAnalyzeCached(index, result, delegate)
            }
        }

        result.determine(lines)

    }

    private fun putAnalyzeCached(
        line: Int,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        for (i in bufferSpanMap[line] ?: mutableListOf()) {
            if (!delegate.shouldAnalyze()) {
                break
            }
            result.addIfNeeded(line, i.first, i.second)
        }
    }

    private fun scanDiffLine(
        line: Int,
        charArray: CharArray,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate,
        state: TMState
    ): TMState {
        val lineTokens = textMateBridgeLanguage.tokenizer.tokenize(
            String(charArray), state, 0, 10000000
        )

        val bufferSpanList = bufferSpanMap.getOrElse(line) { mutableListOf() }

        bufferSpanList.clear()

        val themeScheme = runCatching {
            textMateBridgeLanguage.codeEditor.colorScheme as BaseTextMateTheme
        }.getOrNull()


        for (token in lineTokens.tokens) {
            if (!delegate.shouldAnalyze()) {
                break
            }
            val pair = token.startIndex to (themeScheme?.match(token.type)
                ?: EditorColorScheme.TEXT_NORMAL)
            bufferSpanList.add(pair)
            result.addIfNeeded(line, pair.first, pair.second)
        }
        bufferSpanMap[line] = bufferSpanList
        return lineTokens.endState
    }

}