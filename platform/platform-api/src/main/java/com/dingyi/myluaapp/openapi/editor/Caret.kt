package com.dingyi.myluaapp.openapi.editor

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.UserDataHolderEx


/**
 * Represents a specific caret instance in the editor.
 * Provides methods to query and modify caret position and caret's associated selection.
 *
 *
 * Instances of this interface are supposed to be obtained from [CaretModel] instance, and not created explicitly.
 */
interface Caret : UserDataHolderEx, Disposable {
    /**
     * Returns an instance of Editor, current caret belongs to.
     */
    fun getEditor(): Editor

    /**
     * Returns an instance of CaretModel, current caret is associated with.
     */

    fun getCaretModel(): CaretModel


    /**
     * Moves the caret by the specified number of lines and/or columns.
     *
     * @param columnShift    the number of columns to move the caret by.
     * @param lineShift      the number of lines to move the caret by.
     * @param withSelection  if true, the caret move should extend the selection in the document.
     * @param scrollToCaret  if true, the document should be scrolled so that the caret is visible after the move.
     */

    fun moveCaretRelatively(
        columnShift: Int, lineShift: Int, withSelection: Boolean, scrollToCaret: Boolean
    )

    /**
     * Moves the caret to the specified logical position.
     * If corresponding position is in the folded region currently, the region will be expanded.
     *
     * @param pos the position to move to.
     */
    fun moveToLogicalPosition(pos: LogicalPosition)

    /**
     * Shorthand for calling [.moveToOffset] with `'false'` as a second argument.
     *
     * @param offset      the offset to move to
     */
    fun moveToOffset(offset: Int)

    /**
     * Moves the caret to the specified offset in the document.
     * If corresponding position is in the folded region currently, the region will be expanded.
     *
     * @param offset                  the offset to move to.
     * @param locateBeforeSoftWrap    there is a possible case that there is a soft wrap at the given offset, hence, the same offset
     * corresponds to two different visual positions - just before soft wrap and just after soft wrap.
     * We may want to clearly indicate where to put the caret then. Given parameter allows to do that.
     * **Note:** it's ignored if there is no soft wrap at the given offset
     */
    fun moveToOffset(offset: Int, locateBeforeSoftWrap: Boolean)

    /**
     * Tells whether caret is in consistent state currently. This might not be the case during document update, but client code can
     * observe such a state only in specific circumstances. So unless you're implementing very low-level editor logic (involving
     * `PrioritizedDocumentListener`), you don't need this method - you'll only see it return `true`.
     */
    val isUpToDate: Boolean

    /**
     * Returns the logical position of the caret.
     */

    fun getLogicalPosition(): LogicalPosition


    /**
     * Returns the offset of the caret in the document. Returns 0 for a disposed (invalid) caret.
     * Must be called from inside read action (see [Application.runReadAction])
     *
     * @see .isValid
     */
    fun offset(): Int

    /**
     * Disable cursor smoothing animation
     */
    fun disableAnimation()

    /**
     * Enable cursor smoothing animation
     */
    fun enableAnimation()

    /**
     * Returns the start offset in the document of the selected text range, or the caret
     * position if there is currently no selection.
     * Must be called from inside read action (see [Application.runReadAction])
     *
     * @see selectionRange
     */
    fun selectionStart(): Int

    /**
     * Returns the object that encapsulates information about visual position of selected text start if any
     * Must be called from inside read action (see [Application.runReadAction])
     */

    fun getSelectionStartPosition(): LogicalPosition

    /**
     * Returns the end offset in the document of the selected text range, or the caret
     * position if there is currently no selection.
     * Must be called from inside read action (see [Application.runReadAction])
     *
     * @see selectionRange
     */
    fun selectionEnd(): Int

    /**
     * Returns the object that encapsulates information about visual position of selected text end if any.
     * Must be called from inside read action (see [Application.runReadAction])
     */
    fun getSelectionEndPosition(): LogicalPosition

    /**
     * Returns the text selected in the editor or null if there is currently no selection.
     * Must be called from inside read action (see [Application.runReadAction])
     */
    fun selectedText(): CharSequence


    /**
     * Returns true if a range of text is currently selected, false otherwise.
     * Must be called from inside read action (see [Application.runReadAction])
     */
    fun hasSelection(): Boolean

    /**
     * Returns current selection, or empty range at caret offset if no selection exists.
     * Must be called from inside read action (see [Application.runReadAction]).
     * This method is preferable because the most implementations are thread-safe, so the returned range is always consistent, whereas
     * the more conventional `TextRange.create(getSelectionStart(), getSelectionEnd())` could return inconsistent range when the selection
     * changed between [.getSelectionStart] and [.getSelectionEnd] calls.
     * @see .getSelectionStart
     * @see .getSelectionEnd
     */
    val selectionRange: TextRange
        get() = TextRange.create(
            selectionStart(), selectionEnd()
        )

    /**
     * Selects the specified range of text.
     *
     *
     * System selection will be updated, if such feature is supported by current editor.
     *
     * @param startOffset the start offset of the text range to select.
     * @param endOffset   the end offset of the text range to select.
     */
    fun setSelection(startOffset: Int, endOffset: Int)

    /**
     * Selects the specified range of text.
     *
     * @param startOffset the start offset of the text range to select.
     * @param endOffset   the end offset of the text range to select.
     * @param updateSystemSelection whether system selection should be updated (might not have any effect if current editor doesn't support such a feature)
     */
    fun setSelection(startOffset: Int, endOffset: Int, updateSystemSelection: Boolean)

    /**
     * Selects target range providing information about visual boundary of selection end.
     *
     *
     * That is the case for soft wraps-aware processing where the whole soft wraps virtual space is matched to the same offset.
     *
     *
     * Also, in column mode this method allows to create selection spanning virtual space after the line end.
     *
     *
     * System selection will be updated, if such feature is supported by current editor.
     *
     * @param startOffset     start selection offset
     * @param endPosition     end visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param endOffset       end selection offset
     */
    fun setSelection(startOffset: Int, endPosition: LogicalPosition, endOffset: Int)

    /**
     * Selects target range based on its visual boundaries.
     *
     *
     * That is the case for soft wraps-aware processing where the whole soft wraps virtual space is matched to the same offset.
     *
     *
     * Also, in column mode this method allows to create selection spanning virtual space after the line end.
     *
     *
     * System selection will be updated, if such feature is supported by current editor.
     *
     * @param startPosition   start visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param endPosition     end visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param startOffset     start selection offset
     * @param endOffset       end selection offset
     */
    fun setSelection(
        startPosition: LogicalPosition,
        startOffset: Int,
        endPosition: LogicalPosition,
        endOffset: Int
    )

    /**
     * Selects target range based on its visual boundaries.
     *
     *
     * That is the case for soft wraps-aware processing where the whole soft wraps virtual space is matched to the same offset.
     *
     *
     * Also, in column mode this method allows to create selection spanning virtual space after the line end.
     *
     * @param startPosition   start visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param endPosition     end visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param startOffset     start selection offset
     * @param endOffset       end selection offset
     * @param updateSystemSelection whether system selection should be updated (might not have any effect if current editor doesn't support such a feature)
     */
    fun setSelection(
        startPosition: LogicalPosition,
        startOffset: Int,
        endPosition: LogicalPosition,
        endOffset: Int,
        updateSystemSelection: Boolean
    )

    /**
     * Removes the selection in the editor.
     */
    fun removeSelection()

    /**
     * Selects the entire line of text at the caret position.
     */
    fun selectLineAtCaret()

    /**
     * Selects the entire word at the caret position, optionally using camel-case rules to
     * determine word boundaries.
     *
     * @param honorCamelWordsSettings if true and "Use CamelHumps words" is enabled,
     * upper-case letters within the word are considered as
     * boundaries for the range of text to select.
     */
    fun selectWordAtCaret(honorCamelWordsSettings: Boolean)

    /**
     * Clones the current caret and positions the new one right above or below the current one. If current caret has selection, corresponding
     * selection will be set for the new caret.
     *
     * @param above if `true`, new caret will be created at the previous line, if `false` - on the next line
     * @return newly created caret instance, or `null` if the caret cannot be created because it already exists at the new location
     * or maximum supported number of carets already exists in editor ([CaretModel.getMaxCaretCount]).
     */
    fun clone(above: Boolean): Caret


}