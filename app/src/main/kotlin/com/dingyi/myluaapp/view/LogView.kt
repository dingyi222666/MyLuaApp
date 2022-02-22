package com.dingyi.myluaapp.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.helper.EventHelper
import com.dingyi.myluaapp.common.kts.getAttributeColor
import java.io.File
import java.nio.charset.CharsetEncoder

class LogView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {


    init {
        val file = context.externalCacheDir?.parentFile?.run {
            File("$absolutePath/files/fonts/default.ttf")
        }?.run {
            if (exists())
                this
            else
                null
        }

        typeface = if (file != null) {
            Typeface.createFromFile(file)
        } else {
            Typeface.MONOSPACE
        }

    }

    private val eventHelper = EventHelper()


    private fun sendLog(text: CharSequence, color: Int) {
        val span = generateSpan(text, color)
        append(span)
        append("\n")
        if (parent is NestedScrollView) {
            val parent = parent as NestedScrollView
            parent.post {
                parent.fullScroll(View.FOCUS_DOWN);
            }
        }
    }

    private fun generateSpan(text: CharSequence, color: Int): CharSequence {
        val span = SpannableString(text)

        span.setSpan(ForegroundColorSpan(color), 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        return span
    }

    fun sendLog(level: String, text: String) {
        val color = when (level) {
            "warn" -> context.getAttributeColor(R.attr.theme_log_warn_color)
            "error" -> context.getAttributeColor(R.attr.theme_log_error_color)
            "info" -> context.getAttributeColor(R.attr.theme_log_info_color)
            "debug" -> context.getAttributeColor(R.attr.theme_log_debug_color)
            else -> context.getAttributeColor(R.attr.theme_log_info_color)
        }

        sendLog(text, color)
    }

    fun clear() {
        text = ""
    }

}