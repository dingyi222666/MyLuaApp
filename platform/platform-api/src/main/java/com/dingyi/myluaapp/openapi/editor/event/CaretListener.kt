package com.dingyi.myluaapp.openapi.editor.event

import com.dingyi.myluaapp.openapi.editor.CaretModel
import java.util.EventListener


/**
 * Allows receiving notifications about caret movement, and caret additions/removal.
 *
 * @see CaretModel.addCaretListener
 * @see EditorEventMulticaster.addCaretListener
 */
fun interface CaretListener : EventListener {
    /**
     * Called when the caret position has changed.
     *
     *
     * Only explicit caret movements (caused by `move*()` methods in [Caret] and [CaretModel]) are reported, 'induced' changes of
     * caret offset due to document modifications are not reported.
     *
     * @param event the event containing information about the caret movement.
     */
    fun caretPositionChanged(event: CaretEvent)

}