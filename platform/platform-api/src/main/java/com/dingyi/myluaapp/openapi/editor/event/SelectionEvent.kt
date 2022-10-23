/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.openapi.editor.event

import com.dingyi.myluaapp.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import java.util.EventObject

class SelectionEvent(
    editor: Editor,
    oldSelectionStarts: Int,
    oldSelectionEnds: Int,
    newSelectionStarts: Int,
    newSelectionEnds: Int
) : EventObject(editor) {
    val oldRange: TextRange
    val newRange: TextRange

    init {
        oldRange = getRanges(oldSelectionStarts, oldSelectionEnds)
        newRange = getRanges(newSelectionStarts, newSelectionEnds)
    }

    val editor: Editor
        get() = getSource() as Editor

    companion object {
        private fun getRanges(starts: Int, ends: Int): TextRange {
            return if (starts == 0) {
                TextRange.EMPTY_RANGE
            } else TextRange(starts, ends)
        }
    }
}