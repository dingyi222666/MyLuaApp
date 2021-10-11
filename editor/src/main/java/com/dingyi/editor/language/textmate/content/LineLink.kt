package com.dingyi.editor.language.textmate.content

import android.util.ArrayMap
import android.util.SparseArray

/**
 * @author: dingyi
 * @date: 2021/10/11 14:47
 * @description: line link, When one of the sub line needs to be draw, all of lines will be re draw
 **/
class LineLink {
    fun addLine(line: Line) {
        lines[line.line] = line
    }

    fun removeLine(line: Int) {
        lines.remove(line)
    }

    /**
     * It is possible that the object is created but not used, so the list is created only when it is used
     */
     val lines by lazy(LazyThreadSafetyMode.NONE) { ArrayMap<Int,Line>(128) }
}