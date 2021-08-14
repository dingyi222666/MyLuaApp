package com.dingyi.editor

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


/**
 * @author: dingyi
 * @date: 2021/8/14 20:26
 * @description:
 **/

class CodeEditor(context: Context,attributeSet: AttributeSet) :
    io.github.rosemoe.editor.widget.CodeEditor(context,attributeSet) {

    init {
        Typeface.BOLD
        typefaceText = Typeface.MONOSPACE;
        typefaceLineNumber = typefaceText
    }
}