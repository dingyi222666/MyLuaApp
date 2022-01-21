package com.dingyi.myluaapp.build.log

import android.app.Application
import android.content.Intent
import com.dingyi.myluaapp.build.api.logger.ILogger

class Logger(application: Application):ILogger {


    private val connect = LoggerConnect(application)

    inner class LoggerConnect(private val application: Application) {

        fun sendMsg(tag:String,message:String) {
            sendBroadcast(tag, message)
        }

        private fun sendBroadcast(tag:String,message:String) {
            application.sendBroadcast(
                Intent().apply {
                    action = "com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver"
                    putExtra("message",message)
                    putExtra("tag",tag)
                }
            )
        }

    }

    override fun waring(string: String) {
        connect.sendMsg("warn",string)
    }

    override fun info(string: String) {
        connect.sendMsg("info",string)
    }

    override fun debug(string: String) {
        connect.sendMsg("debug",string)
    }

    override fun error(string: String) {
        connect.sendMsg("error",string)
    }
}