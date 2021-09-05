package com.dingyi.editor

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
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

    private var downX = 0

    private var mForceHorizontalScrollable = true

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
        setAutoCompletionItemAdapter(AutoCompletionItemAdapter(this))
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
            this@CodeEditor.typefaceText = Typeface.createFromFile(it)
            this@CodeEditor.typefaceLineNumber = Typeface.createFromFile(it)
        }

        blockLineWidth = 1.dp * 0.4f
        dividerWidth = 1.dp * 0.4f
        scrollBarSize = 2.dp
        tabWidth = 4

    }

    /**
     * @see CodeEditor.forceHorizontalScrollEnableWhenIntercept
     */
    fun isForceHorizontalScrollEnableWhenIntercept(): Boolean {
        return mForceHorizontalScrollable
    }

    /**
     * When the parent is a scrollable view group,
     * request it not to allow horizontal scrolling to be intercepted.
     * Until the code cannot scroll horizontally
     * @param forceHorizontalScrollable  Whether force horizontal scrolling
     */
    fun forceHorizontalScrollEnableWhenIntercept(forceHorizontalScrollable: Boolean) {
        mForceHorizontalScrollable = forceHorizontalScrollable
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                if (mForceHorizontalScrollable) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - downX
                if (mForceHorizontalScrollable) {
                    if (deltaX > 0 && scroller.currX == 0
                        || deltaX < 0 && scroller.currX == scrollMaxX
                    ) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    // make public
    public override fun postHideCompletionWindow() {
        super.postHideCompletionWindow()
    }
}