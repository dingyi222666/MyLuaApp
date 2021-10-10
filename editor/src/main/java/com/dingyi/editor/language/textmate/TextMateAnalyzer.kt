package com.dingyi.editor.language.textmate

import android.util.SparseArray
import com.dingyi.editor.language.textmate.content.Line
import com.dingyi.editor.language.textmate.content.LineContent
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer

/**
 * @author: dingyi
 * @date: 2021/10/10 16:31
 * @description:
 **/
class TextMateAnalyzer(private val textMateBridgeLanguage: TextMateBridgeLanguage) : CodeAnalyzer {


    private val bufferSpanMap = SparseArray<MutableList<Pair<Int, Int?>>>()


    private val lineContent = LineContent()


    private var globalState = textMateBridgeLanguage.tokenizer.initialState

    override fun analyze(
        content: CharSequence,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        lineContent.commitText(content.toString(), delegate)

        var lines = 0

        for (index in 0 until lineContent.getLines().size()) {
            lines = index + 1
            if (!delegate.shouldAnalyze()) {
                return
            }
            if (lineContent.getDiffLines().containsKey(index)) {
                scanLine(index, lineContent.getLines()[index], result, delegate)
            } else {
                putAnalyzeCached(index, result, delegate)
            }
        }



        result.determine(lines + 1)

    }

    private fun putAnalyzeCached(
        line: Int,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        for (pair in bufferSpanMap[line] ?: mutableListOf()) {
            if (!delegate.shouldAnalyze()) {
                break
            }

            if (pair.second != null) {
                result.addIfNeeded(line, pair.first , pair.second!!)
            } else {
                result.addNormalIfNull()
            }
        }
    }

    private fun scanLine(
        line: Int,
        lineText: Line,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate,
    ) {
        val lineTokens = textMateBridgeLanguage.tokenizer.tokenize(
            lineText.content, globalState, 0, 1000000000
        )

        globalState = lineTokens.endState

        val bufferSpanList = bufferSpanMap.get(line, mutableListOf())

        bufferSpanList.clear()

        val themeScheme = runCatching {
            textMateBridgeLanguage.codeEditor.colorScheme as TextMateTheme
        }.getOrNull()

        for (token in lineTokens.tokens) {
            if (!delegate.shouldAnalyze()) {
                break
            }
            println()
            val pair = token.startIndex  to (themeScheme?.match(token))

            bufferSpanList.add(pair)
            if (pair.second != null) {
                result.addIfNeeded(line, pair.first , pair.second!!)
            } else {
                result.addNormalIfNull()
            }
        }
        if (bufferSpanMap.indexOfKey(line) < 0) {
            bufferSpanMap.put(line, bufferSpanList)
        }
    }

}