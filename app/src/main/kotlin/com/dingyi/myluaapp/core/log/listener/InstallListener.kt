package com.dingyi.myluaapp.core.log.listener

import android.content.Context
import android.text.Spannable
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.clearSpans
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.view.LogView

class InstallListener(private val context: Context) : LogView.LogListener {

    override fun interceptLog(log: LogView.Log): LogView.Log {
        if (!log.text.contains("Apk generated")) {
            return log
        }
        val regex = Regex("(?<=\\[)([^\\]]+)(?=\\])")
        regex.find(log.text)?.let { matchResult ->
            matchResult.groups
                .filterNotNull()
                .forEach {
                    log.text.setSpan(
                        object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                if (it.value == "[install]") {
                                    //TODO
                                }
                            }
                        }, it.range.first,
                        it.range.last + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }

        }

        return log

    }
}