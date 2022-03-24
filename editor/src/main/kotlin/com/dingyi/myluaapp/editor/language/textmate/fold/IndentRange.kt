package com.dingyi.myluaapp.editor.language.textmate.fold

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager.CodeBlockAnalyzeDelegate
import io.github.rosemoe.sora.langs.textmate.folding.FoldingRegions
import io.github.rosemoe.sora.langs.textmate.folding.PreviousRegion
import io.github.rosemoe.sora.langs.textmate.folding.RangesCollector
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.textmate.core.internal.oniguruma.OnigRegExp
import io.github.rosemoe.sora.textmate.core.internal.oniguruma.OnigResult
import io.github.rosemoe.sora.textmate.core.internal.oniguruma.OnigString
import io.github.rosemoe.sora.textmate.languageconfiguration.internal.supports.Folding
import kotlin.properties.Delegates

object IndentRange {
    const val MAX_LINE_NUMBER = 0xFFFFFF
    const val MAX_FOLDING_REGIONS = 0xFFFF
    const val MASK_INDENT = -0x1000000

    // START sora-editor note
    // Change String to char[] and int
    // END sora-editor note

    // START sora-editor note
    // Change String to char[] and int
    // END sora-editor note
    fun computeStartColumn(line: CharArray, len: Int, tabSize: Int): Int {
        var column = 0
        var i = 0
        while (i < len) {
            val chCode = line[i]
            if (chCode == ' ') {
                column++
            } else if (chCode == '\t') {
                column += tabSize
            } else {
                break
            }
            i++
        }
        return if (i == len) {
            // line only consists of whitespace
            -1
        } else column
    }

    /**
     * @return :
     * - -1 => the line consists of whitespace
     * - otherwise => the indent level is returned value
     */
    fun computeIndentLevel(line: CharArray, len: Int, tabSize: Int): Int {
        var indent = 0
        var i = 0
        while (i < len) {
            val chCode = line[i]
            if (chCode == ' ') {
                indent++
            } else if (chCode == '\t') {
                indent = indent - indent % tabSize + tabSize
            } else {
                break
            }
            i++
        }
        return if (i == len) {
            // line only consists of whitespace
            -1
        } else indent
    }


    fun computeRanges(
        model: Content,
        tabSize: Int,
        offSide: Boolean,
        markers: Folding?,
        foldingRangesLimit: Int,
        delegate: HighlightProvider.Delegate
    ): FoldingRegions {
        val result = RangesCollector(foldingRangesLimit, tabSize)
        var pattern: OnigRegExp? = null
        if (markers != null) {
            pattern = OnigRegExp("(${markers.markersStart})|(?:${markers.markersEnd})")
        }
        val previousRegions = mutableListOf<PreviousRegion>()
        var line = model.lineCount + 1
        // sentinel, to make sure there's at least one entry
        previousRegions.add(PreviousRegion(-1, line, line))
        line = model.lineCount - 1
        while (line >= 0 && !delegate.isCancelled()) {
            val lineContent = model.getLineString(line)
            val indent =
                computeIndentLevel(model.getLine(line).rawData, model.getColumnCount(line), tabSize)
            var previous = previousRegions[previousRegions.size - 1]
            if (indent == -1) {
                if (offSide) {
                    // for offSide languages, empty lines are associated to the previous block
                    // note: the next block is already written to the results, so this only
                    // impacts the end position of the block before
                    previous.endAbove = line
                }
                line--
                continue  // only whitespace
            }
            var m:OnigResult? = null
            if (pattern != null && pattern.search(OnigString(lineContent), 0)
                    .also { m = it } != null
            ) {

                // folding pattern match
                if (m?.count() ?: 0 >= 2) { // start pattern match
                    // discard all regions until the folding pattern
                    var i = previousRegions.size - 1
                    while (i > 0 && previousRegions[i].indent != -2) {
                        i--
                    }
                    if (i > 0) {
                        //??? previousRegions.length = i + 1;
                        previous = previousRegions[i]

                        // new folding range from pattern, includes the end line
                        result.insertFirst(line, previous.line, indent)
                        previous.line = line
                        previous.indent = indent
                        previous.endAbove = line
                        line--
                        continue
                    } else {
                        // no end marker found, treat line as a regular line
                    }
                } else { // end pattern match
                    previousRegions.add(PreviousRegion(-2, line, line))
                    line--
                    continue
                }
            }
            if (previous.indent > indent) {
                // discard all regions with larger indent
                do {
                    previousRegions.removeAt(previousRegions.size - 1)
                    previous = previousRegions[previousRegions.size - 1]
                } while (previous.indent > indent)

                // new folding range
                val endLineNumber = previous.endAbove - 1
                if (endLineNumber - line >= 1) { // needs at east size 1
                    result.insertFirst(line, endLineNumber, indent)
                }
            }
            if (previous.indent == indent) {
                previous.endAbove = line
            } else { // previous.indent < indent
                // new region with a bigger indent
                previousRegions.add(PreviousRegion(indent, line, line))
            }
            line--
        }
        return result.toIndentRanges(model)
    }
}
