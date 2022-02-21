package com.dingyi.myluaapp.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.io.File

class LogView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {


    init {
        context.externalCacheDir?.parentFile?.run {
            File("$absolutePath/files/fonts/default.ttf")
        }?.run {
            if (exists())
                this
            else
                null
        }?.let {
            typeface = Typeface.createFromFile(it)
        }

    }

    fun sendLog(text:CharSequence,color:Int) {

    }

}