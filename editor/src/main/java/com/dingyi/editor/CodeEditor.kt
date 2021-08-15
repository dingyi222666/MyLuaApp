package com.dingyi.editor

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.dingyi.editor.kts.dp
import io.github.rosemoe.editor.widget.CodeEditor


/**
 * @author: dingyi
 * @date: 2021/8/14 20:26
 * @description:
 **/

class CodeEditor(context: Context, attributeSet: AttributeSet) :
    CodeEditor(context, attributeSet) {

    init {
        Typeface.BOLD
        typefaceText = Typeface.MONOSPACE;
        typefaceLineNumber = typefaceText
        setAutoCompletionItemAdapter(AutoCompletionItemAdapter())
        autoCompleteWindow.apply {
            var field = this::class.java.getDeclaredField("mBg")
            field.isAccessible = true
            val drawable = field.get(this) as GradientDrawable
            drawable.cornerRadius = 4.dp.toFloat()
            field = this::class.java.getDeclaredField("mListView")
            field.isAccessible = true
            val view = field.get(this) as View
            val params=view.layoutParams as RelativeLayout.LayoutParams
            params.leftMargin=1.dp
            params.rightMargin=1.dp
            params.topMargin=1.dp
            params.bottomMargin=1.dp
            view.layoutParams=params
        }


    }
}