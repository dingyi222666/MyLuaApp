package com.dingyi.editor

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RelativeLayout
import com.dingyi.editor.kts.dp
import io.github.rosemoe.editor.widget.EditorAutoCompleteWindow
import io.github.rosemoe.editor.widget.EditorCompletionAdapter

/**
 * @author: dingyi
 * @date: 2021/8/16 1:32
 * @description:
 **/
class AutoCompleteWindow(mEditor: CodeEditor) : EditorAutoCompleteWindow(mEditor) {


    init {
        val autoCompleteWindowClass =
            io.github.rosemoe.editor.widget.EditorAutoCompleteWindow::class.java
        var field = autoCompleteWindowClass.getDeclaredField("mBg")
        field.isAccessible = true
        val drawable = field.get(this) as GradientDrawable
        drawable.cornerRadius = 4.dp.toFloat()
        field = autoCompleteWindowClass.getDeclaredField("mListView")
        field.isAccessible = true
        val view = field.get(this) as ViewGroup
        val params = view.layoutParams as RelativeLayout.LayoutParams
        params.leftMargin = 1.dp
        params.rightMargin = 1.dp
        params.topMargin = 1.dp
        params.bottomMargin = 1.dp
        view.layoutParams = params
    }


}