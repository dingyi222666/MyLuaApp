package com.dingyi.myluaapp.build.log

import android.app.Application
import android.content.Intent
import com.dingyi.myluaapp.build.api.logger.ILogger

class Logger(application: Application) : ILogger {


    private var link: LoggerSender? = LoggerSender(application)

    inner class LoggerSender(private val application: Application) {

        fun sendMsg(tag: String, message: String,extra:String?) {
            sendBroadcast(tag, message,extra)
        }

        private fun sendBroadcast(tag: String, message: String, extra: String?) {
            application.sendBroadcast(
                Intent().apply {
                    action = "com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver"
                    putExtra("message", message)
                    putExtra("tag", tag)
                    putExtra("extra",extra)
                }
            )
        }

    }

    fun close() {
        link = null
    }

    override fun warning(string: String,extra: String?) {
        link?.sendMsg("warn", string,extra)
    }

    override fun info(string: String,extra: String?) {
        link?.sendMsg("info", string,extra)
    }

    override fun debug(string: String,extra: String?) {
        link?.sendMsg("debug", string,extra)
    }

    override fun error(string: String,extra: String?) {
        link?.sendMsg("error", string,extra)
    }
}