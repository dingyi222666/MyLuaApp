package com.dingyi.myluaapp.editor.ktx

import android.content.res.Resources
import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.component.EditorBuiltinComponent


/**
 * @author: dingyi
 * @date: 2021/8/15 7:42
 * @description:
 **/


inline val Int.dp: Int
    get() = (Resources.getSystem().displayMetrics.density * this + 0.5f).toInt()

inline fun <reified T : EditorBuiltinComponent> CodeEditor.getComponent(): T {
    return this.getComponent(T::class.java)
}

fun MappedSpans.Builder.addIfNeeded(spanLine: Int, column: Int, style: Int) {
    addIfNeeded(spanLine, column, style.toLong())
}