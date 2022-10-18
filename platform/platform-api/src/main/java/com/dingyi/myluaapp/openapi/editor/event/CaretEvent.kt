package com.dingyi.myluaapp.openapi.editor.event

import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.editor.LogicalPosition
import java.util.EventObject


data class CaretEvent(
    val caret: Caret, val oldPosition: LogicalPosition, val newPosition: LogicalPosition
) : EventObject(caret.getEditor()) {


    val editor: Editor
        get() = getSource() as Editor

}