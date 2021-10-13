package com.dingyi.editor.language.textmate

import io.github.rosemoe.sora.data.BlockLine
import io.github.rosemoe.sora.data.Span
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.widget.EditorColorScheme
import org.eclipse.tm4e.core.grammar.StackElement
import org.eclipse.tm4e.core.internal.oniguruma.OnigRegExp
import org.eclipse.tm4e.core.internal.oniguruma.OnigString
import java.io.BufferedReader
import java.io.StringReader

/**
 * @author: dingyi
 * @date: 2021/10/10 16:31
 * @description:
 **/
class TextMateAnalyzer(private val textMateBridgeLanguage: TextMateBridgeLanguage) :
    CodeAnalyzer {


    private fun BufferedReader.forEachLines(block: (String) -> Unit) {
        use {
            var buffer: String? = readLine()
            while (buffer != null) {
                block(buffer)
                buffer = readLine()
            }
        }
    }

    override fun analyze(
        content: CharSequence,
        result: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        val theme = (textMateBridgeLanguage.codeEditor.colorScheme as TextMateScheme)

        textMateBridgeLanguage.registry.setTheme(theme.theme.getThemeRaw())

        var globalState: StackElement = StackElement.NULL

        var (maxSwitch, currSwitch) = 1 to 0

        val foldScannerStart =
            textMateBridgeLanguage.settings?.get("foldingStartMarker")?.let {
                OnigRegExp(it)
            }


        val foldScannerEnd = textMateBridgeLanguage.settings?.get("foldingStopMarker")?.let {
            OnigRegExp(it)
        }

        val blockLines = ArrayDeque<BlockLine>()

        var line = 0
        BufferedReader(StringReader(content.toString())).forEachLines { preLineText ->

            val lineText = StringBuilder(preLineText).append('\n')

            if (line > 0) {
                result.addNormalIfNull()
            }
            if (!delegate.shouldAnalyze()) {
                return@forEachLines
            }


            val colorTokens = textMateBridgeLanguage.grammar.tokenizeLine2(
                lineText.toString(),
                globalState
            )

            foldScannerStart?.search(OnigString(lineText.toString()), 0)?.let {
                if (blockLines.isEmpty()) {
                    if (currSwitch > maxSwitch) {
                        maxSwitch = currSwitch
                    }
                    currSwitch = 0
                }
                result.obtainNewBlock().apply {
                    startLine = line
                    startColumn = it.locationAt(0)
                    blockLines.addFirst(this)
                }
                currSwitch++
            }


            foldScannerEnd?.search(OnigString(lineText.toString()), 0)?.let {
                blockLines.removeFirstOrNull()?.apply {
                    endLine = line
                    endColumn = it.locationAt(0)
                    if (startLine != endLine) {
                        result.addBlockLine(this)
                    } else {
                        currSwitch--
                    }
                }
            }



            globalState = colorTokens.ruleStack


            for (index in 0..colorTokens.tokens.lastIndex / 2) {

                val startIndex = colorTokens.tokens[index * 2]
                val metadata = colorTokens.tokens[index * 2 + 1]
                val nextStartIndex = colorTokens.tokens.getOrElse(index * 2 + 2) { startIndex }

                val fontStyle = theme.match(metadata)


                result.add(
                    line,
                    Span.obtain(
                        startIndex,
                        fontStyle?.foreground?.let { theme.parseColor(it) }
                            ?: theme.getDefaultColor() ?: EditorColorScheme.TEXT_NORMAL
                    )
                )
            }


            line++
        }

        result.determine(line)
        result.suppressSwitch = maxSwitch + 10

    }


}