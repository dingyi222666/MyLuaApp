package com.dingyi.editor.language.textmate

import android.util.SparseArray
import com.dingyi.editor.language.textmate.content.Line
import com.dingyi.editor.language.textmate.content.LineContent
import io.github.rosemoe.sora.data.Span
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.widget.EditorColorScheme

/**
 * @author: dingyi
 * @date: 2021/10/10 16:31
 * @description:
 **/
class TextMateAnalyzer(private val textMateBridgeLanguage: TextMateBridgeLanguage) :
    LineContent.Contract, CodeAnalyzer {


    private val lineContent = LineContent(this)

    private var globalState = textMateBridgeLanguage.tokenizer.initialState

    private var delegate: TextAnalyzer.AnalyzeThread.Delegate? = null

    override fun analyze(
        content: CharSequence,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {
        this.delegate = delegate


        lineContent.setText(content.toString()) //setText

        checkDelegate(delegate)
        lineContent.commit() //commit Text
        checkDelegate(delegate)
        lineContent.invalidateDifferentLines() //invalidate line
        checkDelegate(delegate)
        for (line in 0 until lineContent.getLineCount()) {
            if (line > 0) {
                result.addNormalIfNull() // \n
            }
            checkDelegate(delegate)
            val spans = lineContent.getSpans(line)
            for (spanIndex in 0 until spans.size()) {
                result.add(line, spans[spanIndex])
            }
        }


    }


    private fun checkDelegate(delegate: TextAnalyzer.AnalyzeThread.Delegate) {
        if (!delegate.shouldAnalyze()) {
            throw RuntimeException("stop highlight now")
        }
    }

    override fun invalidateLine(line: Line): SparseArray<Span>? {
        if (delegate?.shouldAnalyze() == false) {
            return null
        }


        val lineTokens = textMateBridgeLanguage.tokenizer.tokenize(
            line.content?.concatToString(),
            globalState,
            0,
            1000000000
        )

        globalState = lineTokens.endState

        val result = SparseArray<Span>(20)
        val theme = (textMateBridgeLanguage.codeEditor.colorScheme as TextMateTheme)
        lineTokens.tokens.forEach { token ->
            result.put(
                result.size(),
                Span.obtain(
                    token.startIndex,
                    theme.match(token)
                        ?: theme.getDefaultColor() ?: EditorColorScheme.TEXT_NORMAL
                )
            )


        }

        return result

    }

}