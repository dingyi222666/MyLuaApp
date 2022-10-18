package com.dingyi.myluaapp.openapi.editor

import com.dingyi.myluaapp.openapi.editor.event.CaretListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer


/**
 * Provides services for moving the caret and retrieving information about caret position.
 *
 *
 * May support several carets existing simultaneously in a document. [.supportsMultipleCarets] method can be used to find out
 * whether a particular instance of CaretModel does it. If it does, query and update methods for caret position operate on a certain 'primary'
 * caret. There exists a way to perform the same operation(s) on each caret - see
 * [.runForEachCaret] method. Within its context, query and update methods operate on the
 * current caret in that iteration. This behaviour can change in future though, so using caret and selection query and update methods in
 * actions that need to operate on multiple carets is discouraged - methods on [Caret] instances obtained
 * via [.getAllCarets] or [.runForEachCaret] should be used instead.
 *
 *
 * How 'primary' caret is determined by the model is not defined (currently it's the most recently added caret, but that can change).
 *
 *
 * At all times, at least one caret will exist in a document.
 *
 *
 * @see Editor.getCaretModel
 */
interface CaretModel {
    /**
     * Moves the caret by the specified number of lines and/or columns.
     *
     * @param columnShift    the number of columns to move the caret by.
     * @param lineShift      the number of lines to move the caret by.
     * @param withSelection  if true, the caret move should extend the selection range in the document.
     * @param blockSelection This parameter is currently ignored.
     * @param scrollToCaret  if true, the document should be scrolled so that the caret is visible after the move.
     */
    fun moveCaretRelatively(
        columnShift: Int,
        lineShift: Int,
        withSelection: Boolean,
        blockSelection: Boolean,
        scrollToCaret: Boolean
    ) {
        currentCaret.moveCaretRelatively(columnShift, lineShift, withSelection, scrollToCaret)
    }

    /**
     * Moves the caret to the specified logical position.
     * If the corresponding position is in the folded region currently, the region will be expanded.
     *
     * @param pos the position to move to.
     */
    fun moveToLogicalPosition(pos: LogicalPosition) {
        currentCaret.moveToLogicalPosition(pos)
    }


    /**
     * Moves the caret to the specified offset in the document.
     * If the corresponding position is in the folded region currently, the region will be expanded.
     *
     * @param offset               the offset to move to.
     * @param locateBeforeSoftWrap there is a possible case that there is a soft wrap at the given offset, hence, the same offset
     * corresponds to two different visual positions - just before soft wrap and just after soft wrap.
     * We may want to clearly indicate where to put the caret then. Given parameter allows to do that.
     * **Note:** it's ignored if there is no soft wrap at the given offset
     */
    /**
     * Shorthand for calling [.moveToOffset] with `'false'` as a second argument.
     *
     * @param offset the offset to move to
     */
    fun moveToOffset(offset: Int, locateBeforeSoftWrap: Boolean = false) {
        currentCaret.moveToOffset(offset, locateBeforeSoftWrap)
    }

    /**
     * Tells whether the caret model is currently in a consistent state.
     * This might not be the case during document update, but client code can
     * observe such a state only in specific circumstances.
     * So unless you're implementing very low-level editor logic (involving
     * `PrioritizedDocumentListener`), you don't need this method - you'll only see it return `true`.
     */
    val isUpToDate: Boolean
        get() = currentCaret.isUpToDate

    /**
     * Returns the logical position of the caret.
     *
     * @return the caret position.
     */

    val logicalPosition: LogicalPosition
        get() = currentCaret.getLogicalPosition()


    /**
     * Returns the offset of the caret in the document.
     *
     * @return the caret offset.
     */
    val offset: Int
        get() = currentCaret.offset()

    /**
     * Adds a listener for receiving notifications about caret movement and caret addition/removal.
     *
     * @param listener the listener instance.
     */
    fun addCaretListener(listener: CaretListener)

    /**
     * Adds a listener for receiving notifications about caret movement and caret addition/removal.
     * The listener is removed when the given parent disposable is disposed.
     *
     * @param listener the listener instance.
     */
    fun addCaretListener(
        listener: CaretListener, parentDisposable: Disposable
    ) {
        addCaretListener(listener)
        Disposer.register(parentDisposable) { removeCaretListener(listener) }
    }

    /**
     * Removes a listener for receiving notifications about caret movement and caret addition/removal.
     *
     * @param listener the listener instance.
     */
    fun removeCaretListener(listener: CaretListener?)


    /**
     * Returns current caret - the one query and update methods in the model operate at the moment.
     * In the current implementation this is either an iteration-current caret within the context of
     * [.runForEachCaret] method, or the 'primary' caret without that context.
     *
     *
     * Users [.runForEachCaret] method should use caret parameter passed to
     * [CaretAction.perform] method instead of this method, as the definition of current caret (as
     * well as caret instance operated on by model methods) can potentially change.
     */

    val currentCaret: Caret

}
