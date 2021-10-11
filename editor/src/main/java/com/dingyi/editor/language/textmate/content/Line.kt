package com.dingyi.editor.language.textmate.content

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/10 23:23
 * @description:
 **/
class Line(
    var line:Int,
    var content:CharArray?,
    link:LineLink
) {



    init {
        link.addLine(this)
    }

    var link by Delegates.observable(link) { _, old, new ->
        old.removeLine(line)
        new.addLine(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Line


        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = line
        result = 31 * result + content.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "Line(line=$line, content=${content.contentToString()})"
    }
}