package com.dingyi.myluaapp.editor.lsp.server.java.document

import org.eclipse.lsp4j.Range

/**
 * A document in local memory.
 */
open class MemoryDocument(override val uri: String) : Document {

    protected val lines = mutableListOf<DocumentLine>()

    override fun getText(): String {
        return lines.joinToString("\n") { it.text }
    }

    override fun getText(range: Range): String {
        if (range.start.line >= lines.size || range.end.line >= lines.size) {
            return ""
        }

        val subList = lines.subList(range.start.line, range.end.line + 1)

        return subList.mapIndexed { index, documentLine ->
            if (index == 0) {
                documentLine.text.substring(range.start.character, documentLine.text.length)
            } else if (index == subList.size - 1) {
                documentLine.text.substring(0, range.end.character)
            } else {
                documentLine.text
            }
        }.joinToString("\n")
    }

    override fun setText(text: String) {
        var workLine = 0
        val buffer = StringBuilder()
        var currLine: DocumentLine = lines[workLine]
        for (element in text) {
            if (element == '\n') {
                val newLine = DocumentLine(buffer.toString(), currLine.lineNumber)
                lines.add(workLine + 1, newLine)
                currLine = newLine
                workLine++
            } else {
                buffer.append(element)
            }
        }
        buffer.clear()
    }
}


class DocumentLine(
    var text: String,
    var lineNumber: Int,
) {
    override fun toString(): String {
        return text
    }

    fun getRange(range: IntRange): String {
        if (range.last > text.length) {
            throw IllegalArgumentException("range out of text")
        }
        return text.substring(range.first, range.last)
    }
}