package com.dingyi.myluaapp.build.log

import android.app.Application
import android.content.Intent
import com.dingyi.myluaapp.build.api.logger.ILogger

class Logger(application: Application) : ILogger {


    private var link: LoggerSender? = LoggerSender(application)

    inner class LoggerSender(private val application: Application) {

        fun sendMsg(tag: String, message: String) {
            sendBroadcast(tag, message)
        }

        private fun sendBroadcast(tag: String, message: String) {
            application.sendBroadcast(
                Intent().apply {
                    action = "com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver"
                    putExtra("message", message)
                    putExtra("tag", tag)
                }
            )
        }

    }

    fun close() {
        link = null
    }

    override fun warning(string: String) {
        link?.sendMsg("warn", string)
    }

    override fun info(string: String) {
        link?.sendMsg("info", string)
    }

    override fun debug(string: String) {
        link?.sendMsg("debug", string)
    }

    override fun error(string: String) {
        link?.sendMsg("error", string)
    }
}