package com.dingyi.myluaapp.build.api.logger

import com.android.utils.ILogger
import java.util.Formatter
import java.util.Locale

interface ILogger : ILogger {

    fun warning(string: String)

    fun info(string: String)

    fun debug(string: String)

    fun error(string: String)


    override fun error(t: Throwable?, msgFormat: String?, vararg args: Any?) {
        val throwableString = t?.stackTraceToString()
        val formatString = Formatter(Locale.getDefault()).format(msgFormat, args)
        if (throwableString != null) {
            error(throwableString)
        }
        error("e:$formatString")
    }


    override fun info(msgFormat: String, vararg args: Any?) {
        val formatString = Formatter(Locale.getDefault()).format(msgFormat, args)
        debug(formatString.toString())
    }

    override fun verbose(msgFormat: String, vararg args: Any?) {
        val formatString = Formatter(Locale.getDefault()).format(msgFormat, args)
        debug(formatString.toString())
    }

    override fun warning(msgFormat: String, vararg args: Any?) {
        val formatString = Formatter(Locale.getDefault()).format(msgFormat, args)
        warning(formatString.toString())
    }
}