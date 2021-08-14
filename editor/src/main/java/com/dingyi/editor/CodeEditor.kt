package com.dingyi.editor

import android.content.Context
import android.graphics.Typeface


/**
 * @author: dingyi
 * @date: 2021/8/14 20:26
 * @description:
 **/

class CodeEditor(context: Context) :
    io.github.rosemoe.editor.widget.CodeEditor(context) {

    init {
        typefaceText = Typeface.MONOSPACE;
        typefaceLineNumber = typefaceText
    }
}