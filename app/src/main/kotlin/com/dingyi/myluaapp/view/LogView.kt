package com.dingyi.myluaapp.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
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
import java.io.ObjectInput
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


        movementMethod = LinkMovementMethod.getInstance()
    }

    data class Log(
        val text: SpannableString,
        val level: String,
        val color: Int,
        val extra:Any?
    )

    interface LogListener {
        fun interceptLog(log: Log): Log
    }


    private val allLog = mutableListOf<Log>()

    private val eventList = mutableListOf<LogListener>()

    fun addLogListener(listener: LogListener) {
        eventList.add(listener)
    }

    fun removeLogListener(listener: LogListener) {
        eventList.remove(listener)
    }

    private fun interceptLog(log: Log): Log {
        return eventList.fold(log) { t, listener ->
            listener.interceptLog(t)
        }
    }

    private fun sendLog(text: CharSequence, level: String, color: Int,extra: Any?) {

        if (allLog.size >= 200) {
            val slice = allLog.slice(100..allLog.lastIndex)
            allLog.clear()
            allLog.addAll(slice)
            addLogList(allLog)
        }

        val log = interceptLog(generateLog(text, level, color,extra))
        append(log.text)
        allLog.add(log)
        append("\n")


        if (parent is NestedScrollView) {
            val parent = parent as NestedScrollView
            parent.post {
                parent.fullScroll(View.FOCUS_DOWN);
            }
        }
    }

    private fun addLogList(allLog: MutableList<Log>) {
        allLog.forEach {
            append(it.text)
            append("\n")
        }
    }

    private fun generateLog(text: CharSequence, level: String, color: Int,extra: Any?): Log {
        val span = SpannableString(text)

        span.setSpan(ForegroundColorSpan(color), 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        return Log(span, level, color,extra)
    }

    fun sendLog(level: String, text: String,extra: Any? = null) {
        val color = when (level) {
            "warn" -> context.getAttributeColor(R.attr.theme_log_warn_color)
            "error" -> context.getAttributeColor(R.attr.theme_log_error_color)
            "info" -> context.getAttributeColor(R.attr.theme_log_info_color)
            "debug" -> context.getAttributeColor(R.attr.theme_log_debug_color)
            else -> context.getAttributeColor(R.attr.theme_log_info_color)
        }

        sendLog(text, level, color,extra)
    }

    fun clear() {
        allLog.clear()
        text = ""
    }

}