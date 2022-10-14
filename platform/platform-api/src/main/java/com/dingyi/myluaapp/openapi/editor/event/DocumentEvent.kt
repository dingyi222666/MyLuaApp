package com.dingyi.myluaapp.openapi.editor.event

import com.dingyi.myluaapp.openapi.editor.Document
import java.util.*


abstract class DocumentEvent(
    val document: Document
) : EventObject(document) {


    /**
     * The start offset of a text change.
     */
    abstract fun getOffset(): Int

    fun getMoveOffset(): Int {
        return getOffset();
    }

    abstract fun getOldFragment(): CharSequence

    abstract fun getNewFragment(): CharSequence

    abstract fun getOldLength(): Int
    abstract fun getNewLength(): Int

    abstract fun getOldTimeStamp(): Long

    public fun isWholeTextReplaced() =
        getOffset() == 0 && getNewLength() == document.getTextLength()
}
