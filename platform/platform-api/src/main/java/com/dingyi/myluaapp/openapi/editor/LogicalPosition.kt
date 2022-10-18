package com.dingyi.myluaapp.openapi.editor


/**
 * Represents a logical position in the editor, including line and column values (both zero-based). Line value relates to the corresponding
 * line in the editor's [Document]. Column value counts characters from the beginning of the logical line (tab character can occupy
 * multiple columns - up to the tab size set for editor, surrogate pairs of characters are counted as one column). Positions beyond the end
 * of line can be represented (in this case column number will be larger than the number of characters in the line).
 *
 *
 * Logical position corresponds to a boundary between two characters and can be associated with either a preceding or succeeding character
 * (see [.leansForward]). This association makes a difference in a bidirectional text, where a mapping from logical to visual position
 * is not continuous.
 *
 *
 * Logical position of caret in current editor is displayed in IDE's status bar (displayed line and column values are one-based, so they are
 * incremented before showing).
 *
 *
 * **Note:** two objects of this class are considered equal if their logical line and column are equal. I.e. all logical positions
 * for soft wrap-introduced virtual space and the first document symbol after soft wrap are considered to be equal. Value of
 * [.leansForward] flag doesn't impact the equality of logical positions.
 *

 */
data class LogicalPosition @JvmOverloads constructor(
    var line: Int,
    var column: Int,
) : Comparable<LogicalPosition> {


    /**
     * If `true`, this position is associated with succeeding character (in logical order), otherwise it's associated with
     * preceding character. This can make difference in bidirectional text, where logical positions which differ only in this flag's value
     * can have different visual positions.
     *
     *
     * This field has no impact on equality and comparison relationships between `LogicalPosition` instances.
     */


    init {
        require(line >= 0) { "line must be non negative: $line" }
        require(column >= 0) { "column must be non negative: $column" }
    }

    /**
     * Constructs a new `LogicalPosition` instance with a given value of [.leansForward] flag.
     */
    fun leanForward(value: Boolean): LogicalPosition {
        return LogicalPosition(line, column)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LogicalPosition) return false
        return column == other.column && line == other.line
    }

    override fun hashCode(): Int {
        return 29 * line + column
    }


    override fun toString(): String {
        return "LogicalPosition: (" + line + ", " + column + ")"
    }

    override operator fun compareTo(other: LogicalPosition): Int {
        return if (line != other.line) line - other.line else column - other.column
    }
}
