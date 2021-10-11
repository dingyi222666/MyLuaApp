package com.dingyi.editor.language.textmate.content

import android.util.ArrayMap
import android.util.SparseArray
import io.github.rosemoe.sora.data.Span

/**
 * @author: dingyi
 * @date: 2021/10/10 16:43
 * @description:a diff content util
 **/
class LineContent(private val contract: Contract) {

    /**
     * cache lines,large read and write operations
     */
    private val bufferLines = SparseArray<Line>(10000)

    /**
     * deffer lines, bridge bufferLines to originalLines
     */
    private val differLines = ArrayMap<Int, Line>(10000)

    /**
     * the content of line,
     */
    private val originalLines = SparseArray<Line>(1000)


    private val bufferSpans = SparseArray<SparseArray<Span>>()

    private fun addLine(lineText: CharArray, line: Int = bufferLines.size()) {
        bufferLines.put(
            line,
            Line(
                line = bufferLines.size() - 1 + 1,
                content = lineText,
                link = LineLink()
            )
        )
    }

    private fun addLine(lineText: String, line: Int = bufferLines.size()) {
        addLine(lineText.toCharArray(), line)
    }


    fun removeLine(index: Int) {
        bufferLines.delete(index)

    }


    /**
     * set text to bufferLine
     */
    fun setText(content: String) {
        var scanLine = 0
        content.reader().forEachLine {
            addLine(it, scanLine)
            scanLine++
        }

        if (bufferLines.size() >= scanLine) {
            for (index in scanLine - 1 until bufferLines.size()) {
                removeLine(index)
            }
        }

    }


    /**
     * commit bufferLines to originalLines
     */
    fun commit() {

        differLines.clear() // clear differLines

        //process delete

        if (bufferLines.size() < originalLines.size()) {
            for (index in originalLines.size() - 1 until bufferLines.size()) {
                originalLines.remove(index)
                //delete buffer spans
                if (bufferSpans.indexOfKey(index) >= 0)
                    bufferSpans.remove(index)
            }
        }

        //process add/deffer

        for (index in 0 until bufferLines.size()) {
            val bufferLine = bufferLines[index]
            val originalLine = originalLines.get(
                index, Line(
                    0,
                    null, LineLink()
                )
            )

            if (!bufferLine.content.contentEquals(originalLine.content)) {
                originalLine.content = bufferLine.content

                originalLine.line = bufferLine.line
                differLines[index] = originalLine
            }

            originalLines.put(index, originalLine)

        }



        differLines.values.forEach { line ->
            line.link.lines.forEach {
                differLines[it.key] = it.value
            }

        }


    }


    /**
     * invalidate different line
     */
    fun invalidateDifferentLines() {
        differLines.keys.sorted().forEach {
            invalidateLine(it)
        }
    }

    private fun invalidate() {
        for (i in 0 until originalLines.size()) {
            invalidateLine(i)
        }
    }


    private fun invalidateLine(line: Int) {
        println(originalLines[line])
        val spans = contract.invalidateLine(originalLines[line])
            ?: throw RuntimeException("stop highlighting now.")

        bufferSpans.put(line, spans)

        val lastLine = when (line) {
            0, originalLines.size() - 1 -> line
            else -> line - 1
        }

        if (bufferSpans[lastLine][bufferSpans[lastLine].size() - 1].colorId == spans[spans.size() - 1].colorId) {
            originalLines[line].link = originalLines[lastLine].link
        } else {
            originalLines[line].link = LineLink()
        }
    }

    fun getSpans(line: Int): SparseArray<Span> {
        return bufferSpans[line]
    }

    fun getLineCount(): Int {
        return originalLines.size()
    }

    interface Contract {
        /**
         * invalidate Lines,if return null should not continue draw
         * @param line invalidate Line
         * @return spans
         */
        fun invalidateLine(line: Line): SparseArray<Span>?
    }

}