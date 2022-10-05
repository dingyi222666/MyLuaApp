// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

import com.intellij.util.SystemProperties
import java.nio.file.Path
import java.util.logging.ConsoleHandler
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord

class JulLogger(protected val myLogger: java.util.logging.Logger) : Logger() {


    override val isDebugEnabled = myLogger.isLoggable(Level.FINE)


    override val isTraceEnabled = myLogger.isLoggable(Level.FINER)


    override fun debug(message: String) {
        myLogger.log(Level.FINE, message)
    }

    override fun debug(t: Throwable?) {
        myLogger.log(Level.FINE, "", t)
    }

    override fun debug(message: String, t: Throwable?) {
        myLogger.log(Level.FINE, message, t)
    }

    override fun trace(message: String) {
        myLogger.log(Level.FINER, message)
    }

    override fun trace(t: Throwable?) {
        myLogger.log(Level.FINER, "", t)
    }

    override fun info(message: String) {
        myLogger.log(Level.INFO, message)
    }

    override fun info(message: String, t: Throwable?) {
        myLogger.log(Level.INFO, message, t)
    }

    override fun warn(message: String, t: Throwable?) {
        myLogger.log(Level.WARNING, message, t)
    }

    override fun error(message: String, t: Throwable?, vararg details: String) {
        val fullMessage = if (details.size > 0) """
     $message
     Details: ${java.lang.String.join("\n", *details)}
     """.trimIndent() else message
        myLogger.log(Level.SEVERE, fullMessage, t)
    }

    override fun setLevel(level: LogLevel) {
        myLogger.level = level.level
    }

    private class OptimizedConsoleHandler : ConsoleHandler() {
        override fun publish(record: LogRecord) {
            // checking levels _before_ calling a synchronized method
            if (isLoggable(record)) {
                super.publish(record)
            }
        }
    }

    companion object {
        @JvmOverloads
        fun clearHandlers(logger: java.util.logging.Logger = java.util.logging.Logger.getLogger("")) {
            for (handler in logger.handlers) {
                logger.removeHandler(handler)
            }
        }

        fun configureLogFileAndConsole(
            logFilePath: Path,
            appendToFile: Boolean,
            showDateInConsole: Boolean,
            enableConsoleLogger: Boolean,
            onRotate: Runnable?
        ) {
            var limit: Long = 10000000
            val limitProp = System.getProperty("idea.log.limit")
            if (limitProp != null) {
                try {
                    limit = limitProp.toLong()
                } catch (e: NumberFormatException) {
                    // ignore
                }
            }
            var count = 12
            val countProp = System.getProperty("idea.log.count")
            if (countProp != null) {
                try {
                    count = countProp.toInt()
                } catch (e: NumberFormatException) {
                    // ignore
                }
            }
            val logConsole = SystemProperties.getBooleanProperty("idea.log.console", true)
            val rootLogger = java.util.logging.Logger.getLogger("")
            val layout =
                MyLuaAppLogRecordFormatter()
            val fileHandler: Handler =
                RollingFileHandler(logFilePath, limit, count, appendToFile, onRotate)
            fileHandler.formatter = layout
            fileHandler.level = Level.FINEST
            rootLogger.addHandler(fileHandler)
            if (enableConsoleLogger && logConsole) {
                val consoleHandler: Handler = OptimizedConsoleHandler()
                consoleHandler.formatter =
                    MyLuaAppLogRecordFormatter(
                        showDateInConsole,
                        layout
                    )
                consoleHandler.level = Level.WARNING
                rootLogger.addHandler(consoleHandler)
            }
        }
    }
}