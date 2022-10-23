package com.dingyi.myluaapp.openapi.editor

import com.dingyi.myluaapp.openapi.editor.event.SelectionListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.NlsSafe


/**
 * Provides services for selecting text in the IDE's text editor and retrieving information about the selection.
 * Most of the methods here exist for compatibility reasons, corresponding functionality is also provided by [CaretModel] now.
 *
 *
 *
 * @see Editor.getSelectionModel
 */
interface SelectionModel {
    /**
     * @return the editor this selection model belongs to
     */
    fun getEditor(): Editor


    /**
     * Returns the start offset in the document of the selected text range, or the caret
     * position if there is currently no selection.
     *
     * @return the selection start offset.
     */
    fun getSelectionStart(): Int {
        return getEditor().getCaretModel().currentCaret.selectionStart()
    }

    /**
     * @return    object that encapsulates information about visual position of selected text start if any
     */

    fun getSelectionStartPosition(): LogicalPosition {
        return getEditor().getCaretModel().currentCaret.getSelectionStartPosition()
    }

    /**
     * Returns the end offset in the document of the selected text range, or the caret
     * position if there is currently no selection.
     *
     * @return the selection end offset.
     */
    fun getSelectionEnd(): Int {
        return getEditor().getCaretModel().currentCaret.selectionEnd()
    }

    /**
     * @return    object that encapsulates information about visual position of selected text end if any;
     */

    fun getSelectionEndPosition(): LogicalPosition? {
        return getEditor().getCaretModel().currentCaret.getSelectionEndPosition()
    }


    /**
     * If `allCarets` is `true`, returns the concatenation of selections for all carets, or `null` if there
     * are no selections. If `allCarets` is `false`, works just like [.getSelectedText].
     */

    fun getSelectedText(): @NlsSafe String {

        return getEditor().getCaretModel().currentCaret.selectedText().toString()

    }


    /**
     * Checks if a range of text is currently selected. If `anyCaret` is `true`, check all existing carets in
     * the document, and returns `true` if any of them has selection, otherwise checks only the current caret.
     *
     * @return `true` if a range of text is selected, `false` otherwise.
     */
    fun hasSelection(anyCaret: Boolean): Boolean {
        return getEditor().getCaretModel().currentCaret.hasSelection()
    }

    /**
     * Selects the specified range of text.
     *
     * @param startOffset the start offset of the text range to select.
     * @param endOffset   the end offset of the text range to select.
     */
    fun setSelection(startOffset: Int, endOffset: Int) {
        getEditor().getCaretModel().currentCaret.setSelection(startOffset, endOffset)
    }

    /**
     * Selects target range providing information about visual boundary of selection end.
     *
     *
     * That is the case for soft wraps-aware processing where the whole soft wraps virtual space is matched to the same offset.
     *
     * @param startOffset     start selection offset
     * @param endPosition     end visual position of the text range to select (`null` argument means that
     * no specific visual position should be used)
     * @param endOffset       end selection offset
     */
    fun setSelection(startOffset: Int, endPosition: LogicalPosition, endOffset: Int) {
        getEditor().getCaretModel().currentCaret.setSelection(startOffset, endPosition, endOffset)
    }

    /**
     * Selects target range based on its visual boundaries.
     *
     *
     * That is the case for soft wraps-aware processing where the whole soft wraps virtual space is matched to the same offset.
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
    ) {
        getEditor().getCaretModel().currentCaret.setSelection(
            startPosition,
            startOffset,
            endPosition,
            endOffset
        )
    }


    /**
     * Removes the selection in the editor. If `allCarets` is `true`, removes selections from all carets in the
     * editor, otherwise, does this just for the current caret.
     */
    fun removeSelection() {

        getEditor().getCaretModel().currentCaret.removeSelection()

    }

    /**
     * Adds a listener for receiving information about selection changes.
     *
     * @param listener the listener instance.
     */
    fun addSelectionListener(listener: SelectionListener)

    /**
     * Adds a listener for receiving information about selection changes, which is removed when the given disposable is disposed.
     *
     * @param listener the listener instance.
     */
    fun addSelectionListener(
        listener: SelectionListener,
        parentDisposable: Disposable
    ) {
        addSelectionListener(listener)
        Disposer.register(parentDisposable) { removeSelectionListener(listener) }
    }

    /**
     * Removes a listener for receiving information about selection changes.
     *
     * @param listener the listener instance.
     */
    fun removeSelectionListener(listener: SelectionListener)

    /**
     * Selects the entire line of text at the caret position.
     */
    fun selectLineAtCaret() {
        getEditor().getCaretModel().currentCaret.selectLineAtCaret()
    }

    /**
     * Selects the entire word at the caret position, optionally using camel-case rules to
     * determine word boundaries.
     *
     * @param honorCamelWordsSettings if true and "Use CamelHumps words" is enabled,
     * upper-case letters within the word are considered as
     * boundaries for the range of text to select.
     */
    fun selectWordAtCaret(honorCamelWordsSettings: Boolean) {
        getEditor().getCaretModel().currentCaret.selectWordAtCaret(honorCamelWordsSettings)
    }

    /**
     * Copies the currently selected text to the clipboard.
     *
     * When multiple selections exist in the document, all of them are copied, as a single piece of text.
     */
    fun copySelectionToClipboard()
}