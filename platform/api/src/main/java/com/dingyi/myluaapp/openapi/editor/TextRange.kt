package com.dingyi.myluaapp.openapi.editor

import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.NotNull


/**
 * A text range defined by start and end (exclusive) offset.
 *
 * @see ProperTextRange
 *
 * @see com.intellij.util.text.TextRangeUtil
 */
class TextRange private constructor(
    val startOffset: Int,
    val endOffset: Int,
    checkForProperTextRange: Boolean
) {

    /**
     * @see .create
     * @see .from
     * @see .allOf
     */
    constructor(startOffset: Int, endOffset: Int) : this(startOffset, endOffset, true) {}

    /**
     * @param checkForProperTextRange `true` if offsets should be checked by [.assertProperRange]
     * @see UnfairTextRange
     */
    init {
        if (checkForProperTextRange) {
            assertProperRange(this)
        }
    }

    val length: Int
        get() = endOffset - startOffset

    override fun equals(obj: Any?): Boolean {
        if (obj !is TextRange) return false
        val range = obj
        return startOffset == range.startOffset && endOffset == range.endOffset
    }

    override fun hashCode(): Int {
        return startOffset + endOffset
    }

    operator fun contains(range: TextRange): Boolean {
        return containsRange(range.startOffset, range.endOffset)
    }


    fun containsRange(startOffset: Int, endOffset: Int): Boolean {
        return this.startOffset <= startOffset && endOffset <= this.endOffset
    }

    fun containsOffset(offset: Int): Boolean {
        return offset in startOffset..endOffset
    }

    override fun toString(): String {
        return "($startOffset,$endOffset)"
    }

    operator fun contains(offset: Int): Boolean {
        return offset in startOffset until endOffset
    }

    @Contract(pure = true)
    fun substring(str: String): String {
        return str.substring(startOffset, endOffset)
    }

    fun subSequence(str: CharSequence): CharSequence {
        return str.subSequence(startOffset, endOffset)
    }

    fun cutOut(subRange: TextRange): TextRange {
        require(subRange.startOffset <= length) { "SubRange: $subRange; this=$this" }
        require(subRange.endOffset <= length) { "SubRange: $subRange; this=$this" }
        assertProperRange(subRange)
        return TextRange(
            startOffset + subRange.startOffset,
            endOffset.coerceAtMost(startOffset + subRange.endOffset)
        )
    }


    fun shiftRight(delta: Int): TextRange {
        return if (delta == 0) this else TextRange(startOffset + delta, endOffset + delta)
    }


    fun shiftLeft(delta: Int): TextRange {
        return if (delta == 0) this else TextRange(startOffset - delta, endOffset - delta)
    }


    fun grown(lengthDelta: Int): TextRange {
        return if (lengthDelta == 0) {
            this
        } else from(startOffset, length + lengthDelta)
    }


    fun replace(original: String, replacement: String): String {
        val beginning = original.substring(0, startOffset)
        val ending = original.substring(endOffset)
        return beginning + replacement + ending
    }

    fun intersects(textRange: TextRange): Boolean {
        return intersects(textRange.startOffset, textRange.endOffset)
    }


    fun intersects(startOffset: Int, endOffset: Int): Boolean {
        return this.startOffset.coerceAtLeast(startOffset) <= this.endOffset.coerceAtMost(endOffset)
    }

    fun intersectsStrict(textRange: TextRange): Boolean {
        return intersectsStrict(textRange.startOffset, textRange.endOffset)
    }

    fun intersectsStrict(startOffset: Int, endOffset: Int): Boolean {
        return this.startOffset.coerceAtLeast(startOffset) < this.endOffset.coerceAtMost(endOffset)
    }

    fun intersection(range: TextRange): TextRange? {
        if (equals(range)) {
            return this
        }
        val newStart = startOffset.coerceAtLeast(range.startOffset)
        val newEnd = endOffset.coerceAtMost(range.endOffset)
        return if (isProperRange(newStart, newEnd)) TextRange(newStart, newEnd) else null
    }

    val isEmpty: Boolean
        get() = startOffset >= endOffset


    fun union(textRange: TextRange): TextRange {
        return if (equals(textRange)) {
            this
        } else TextRange(
            startOffset.coerceAtMost(textRange.startOffset),
            endOffset.coerceAtLeast(textRange.endOffset)
        )
    }

    fun equalsToRange(startOffset: Int, endOffset: Int): Boolean {
        return startOffset == this.startOffset && endOffset == this.endOffset
    }

    companion object {

        val EMPTY_RANGE = TextRange(0, 0)
        val EMPTY_ARRAY = arrayOfNulls<TextRange>(0)
        fun containsRange(outer: TextRange, inner: TextRange): Boolean {
            return outer.startOffset <= inner.startOffset && inner.endOffset <= outer.endOffset
        }


        fun from(offset: Int, length: Int): TextRange {
            return create(offset, offset + length)
        }


        fun create(startOffset: Int, endOffset: Int): TextRange {
            return TextRange(startOffset, endOffset)
        }


        fun create(segment: TextRange): TextRange {
            return create(segment.startOffset, segment.endOffset)
        }

        fun areSegmentsEqual(segment1: TextRange, segment2: TextRange): Boolean {
            return (segment1.startOffset == segment2.startOffset
                    && segment1.endOffset == segment2.endOffset)
        }


        fun allOf(s: String): TextRange {
            return TextRange(0, s.length)
        }

        @Throws(AssertionError::class)
        fun assertProperRange(range: TextRange) {
            assertProperRange(range, "")
        }

        @Throws(AssertionError::class)
        fun assertProperRange(range: TextRange, message: Any) {
            assertProperRange(range.startOffset, range.endOffset, message)
        }

        fun assertProperRange(startOffset: Int, endOffset: Int, message: Any) {
            require(
                isProperRange(
                    startOffset,
                    endOffset
                )
            ) { "Invalid range specified: ($startOffset, $endOffset); $message" }
        }

        fun isProperRange(startOffset: Int, endOffset: Int): Boolean {
            return startOffset in 0..endOffset
        }
    }
}