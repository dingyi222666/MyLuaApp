package com.dingyi.editor

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.androlua.LuaApplication
import com.dingyi.editor.kts.dp
import io.github.rosemoe.editor.widget.CodeEditor
import java.io.File


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
        val newCompleteWindow = AutoCompleteWindow(this)
        val completeWindowField = Class.forName("io.github.rosemoe.editor.widget.CodeEditor")
            .getDeclaredField("mCompletionWindow").apply {
                isAccessible = true
            }

        completeWindowField.set(this, newCompleteWindow)//换掉显示窗口
        setAutoCompletionItemAdapter(AutoCompletionItemAdapter())
        setTextActionMode(TextActionMode.ACTION_MODE)
        setPinLineNumber(false)

        LuaApplication.getInstance().externalCacheDir?.parentFile?.run {
            File("$absolutePath/files/fonts/default.ttf")
        }?.run {
            if (exists())
                this
            else
                null
        }?.let {
            this@CodeEditor.typefaceText=Typeface.createFromFile(it)
            this@CodeEditor.typefaceLineNumber= Typeface.createFromFile(it)
        }

        blockLineWidth = 1.dp * 0.4f
        dividerWidth = 1.dp * 0.4f
        scrollBarSize = 2.dp
        tabWidth = 4

    }

    // make public
    public override fun postHideCompletionWindow() {
        super.postHideCompletionWindow()
    }
}