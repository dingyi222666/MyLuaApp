package com.dingyi.myluaapp.core.log.listener

import android.content.Context
import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.View
import com.dingyi.myluaapp.common.ktx.installApk
import com.dingyi.myluaapp.view.LogView

class ApkInstaller(private val context: Context) : LogView.LogListener {

    override fun interceptLog(log: LogView.Log): LogView.Log {
        if (!log.text.toString().contains("APK generated")) {
            return log
        }
        val regex = Regex("\\[[0-9A-Za-z]*\\]")
        regex.findAll(log.text)
            .flatMap { it.groups }
            .filterNotNull()
            .forEach {

                log.text.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            if (it.value == "[install]") {
                                context.installApk(log.extra.toString())
                            }
                        }
                    }, it.range.first,
                    it.range.last + 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }

        return log

    }
}