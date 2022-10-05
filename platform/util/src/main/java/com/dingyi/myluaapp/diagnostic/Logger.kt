// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

import com.intellij.openapi.diagnostic.ControlFlowException
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.util.ArrayUtilRt
import com.intellij.util.ExceptionUtil
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.CancellationException
import java.util.function.Function
import java.util.logging.Level

/**
 *
 * A standard interface to write to `%system%/log/idea.log` (or `%system%/testlog/idea.log` in tests).
 *
 *
 * The `error` methods, in addition to writing to the log file,
 * result in showing the "IDE fatal errors" dialog in the IDE
 * (in EAP versions or if the `idea.fatal.error.notification` system property is set to `true`).
 * See [com.intellij.diagnostic.DefaultIdeaErrorLogger.canHandle] for more details.
 *
 *
 * The `error` methods, when run in unit test mode, throw an [AssertionError].
 * In production, they do not throw exceptions, so the execution continues.
 *
 *
 * In most non-performance tests, the debug level is enabled by default -
 * so that when a test fails, the full content of its log is printed to stdout.
 */
abstract class Logger {
    interface Factory {
        fun getLoggerInstance(category: String): Logger
    }

    private class DefaultFactory : Factory {
        override fun getLoggerInstance(category: String): Logger {
            return DefaultLogger(category)
        }
    }


    abstract val isDebugEnabled: Boolean


    abstract val isTraceEnabled: Boolean
    abstract fun debug(message: String)
    abstract fun debug(t: Throwable?)
    abstract fun debug(message: String, t: Throwable?)

    /**
     * Concatenate the message and all details, without any separator, then log the resulting string.
     *
     *
     * This format differs from [.debugValues] and
     * [.error], which write each detail on a line of its own.
     *
     * @param message the first part of the log message, a plain string without any placeholders
     */
    fun debug(message: String, vararg details: Any) {
        if (isDebugEnabled) {
            val sb = StringBuilder()
            sb.append(message)
            for (detail in details) {
                sb.append(detail)
            }
            debug(sb.toString())
        }
    }

    /**
     * Compose a multi-line log message from the header and the values, writing each value on a line of its own.
     *
     *
     * See [.debug] for a variant using a more compressed format.
     *
     * @param header the main log message, a plain string without any placeholders
     */
    fun debugValues(header: String, values: Collection<*>) {
        if (isDebugEnabled) {
            val text = StringBuilder()
            text.append(header).append(" (").append(values.size).append(")")
            if (!values.isEmpty()) {
                text.append(":")
                for (value in values) {
                    text.append("\n")
                    text.append(value)
                }
            }
            debug(text.toString())
        }
    }

    fun infoWithDebug(t: Throwable) {
        infoWithDebug(t.toString(), t)
    }

    fun infoWithDebug(message: String, t: Throwable) {
        info(message)
        debug(t)
    }

    fun warnWithDebug(t: Throwable) {
        warnWithDebug(t.toString(), t)
    }

    fun warnWithDebug(message: String, t: Throwable) {
        warn(message)
        debug(t)
    }


    /**
     * Log a message with 'trace' level, which is finer-grained than the 'debug' level.
     *
     *
     * Use this method instead of [.debug] for internal events of a subsystem,
     * to avoid overwhelming the log if 'debug' level is enabled.
     */
    open fun trace(message: String) {
        debug(message)
    }

    open fun trace(t: Throwable?) {
        debug(t)
    }

    fun info(t: Throwable) {
        info(t.message.toString(), t)
    }

    abstract fun info(message: String)
    abstract fun info(message: String, t: Throwable?)
    fun warn(message: String) {
        warn(message, null)
    }

    fun warn(t: Throwable) {
        warn(t.message.toString(), t)
    }

    abstract fun warn(message: String, t: Throwable?)
    fun error(message: String) {
        error(message, Throwable(message), *ArrayUtilRt.EMPTY_STRING_ARRAY)
    }

    fun error(message: Any) {
        error(message.toString())
    }

    fun error(message: String, vararg attachments: Attachment) {
        error(message, null, *attachments)
    }

    fun error(message: String, t: Throwable?, vararg attachments: Attachment) {
        error(
            message,
            t,
            *ContainerUtil.map2Array(
                attachments,
                String::class.java
            ) { t: Attachment -> ATTACHMENT_TO_STRING.apply(t) })
    }

    /**
     * Compose an error message from the message and the details, then log it.
     *
     *
     * The exact format of the resulting log message depends on the actual logger.
     * The typical format is a multi-line message, with each detail on a line of its own.
     *
     * @param message a plain string, without any placeholders
     */
    fun error(message: String, vararg details: String) {
        error(message, Throwable(message), *details)
    }

    fun error(message: String, t: Throwable?) {
        error(message, t, *ArrayUtilRt.EMPTY_STRING_ARRAY)
    }

    fun error(t: Throwable) {
        error(t.message.toString(), t, *ArrayUtilRt.EMPTY_STRING_ARRAY)
    }

    /**
     * Compose an error message from the message and the details, then log it.
     *
     *
     * The exact format of the resulting log message depends on the actual logger.
     * The typical format is a multi-line message, with each detail on a line of its own.
     *
     * @param message a plain string, without any placeholders
     */
    abstract fun error(message: String, t: Throwable?, vararg details: String)

    @Contract("false,_->fail") // wrong, but avoid quite a few warnings in the code
    fun assertTrue(value: Boolean, message: Any?): Boolean {
        if (!value) {
            var resultMessage = "Assertion failed"
            if (message != null) resultMessage += ": $message"
            error(resultMessage, Throwable(resultMessage))
        }
        return value
    }

    @Contract("false->fail") // wrong, but avoid quite a few warnings in the code
    fun assertTrue(value: Boolean): Boolean {
        return value || assertTrue(false, null)
    }

    abstract fun setLevel(level: LogLevel)

    /**
     * [.warn] in production, [.error] in tests
     */
    fun warnInProduction(t: Throwable) {
        if (isUnitTestMode) {
            error(t)
        } else {
            warn(t)
        }
    }

    companion object {
        private var isUnitTestMode = false
        private var factory: Factory = DefaultFactory()

        fun setFactory(factory: Class<out Factory>) {
            if (isInitialized) {
                if (factory.isInstance(Companion.factory)) {
                    return
                }
                logFactoryChanged(factory)
            }
            try {
                val constructor = factory.getDeclaredConstructor()
                constructor.isAccessible = true
                Companion.factory = constructor.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
                throw RuntimeException(e)
            }
        }

        fun setFactory(factory: Factory) {
            if (isInitialized) {
                logFactoryChanged(factory.javaClass)
            }
            Companion.factory = factory
        }

        private fun logFactoryChanged(factory: Class<out Factory>) {
            println(
                """Changing log factory from ${Companion.factory.javaClass.canonicalName} to ${factory.canonicalName}
${ExceptionUtil.getThrowableText(Throwable())}"""
            )
        }

        val isInitialized: Boolean
            get() = factory !is DefaultFactory

        fun getInstance(category: String): Logger {
            return factory.getLoggerInstance(category)
        }

        fun getInstance(cl: Class<*>): Logger {
            return factory.getLoggerInstance("#" + cl.name)
        }

        val ATTACHMENT_TO_STRING = Function { attachment: Attachment ->
            """
     ${attachment.path}
     ${attachment.displayText}
     """.trimIndent()
        }

        @JvmStatic
        protected fun ensureNotControlFlow(t: Throwable?): Throwable {
            return if (t is ControlFlowException) Throwable(
                "Control-flow exceptions (e.g. this " + t.javaClass + ") should never be logged. " +
                        "Instead, these should have been rethrown or, if not possible, caught and ignored",
                t
            ) else t!!
        }

        @TestOnly
        fun setUnitTestMode() {
            isUnitTestMode = true
        }
    }
}


inline fun <reified T : Any> T.thisLogger() =
    com.intellij.openapi.diagnostic.Logger.getInstance(T::class.java)

inline fun <reified T : Any> logger() =
    com.intellij.openapi.diagnostic.Logger.getInstance(T::class.java)

inline fun com.intellij.openapi.diagnostic.Logger.debug(
    e: Exception? = null,
    lazyMessage: () -> String
) {
    if (isDebugEnabled) {
        debug(lazyMessage(), e)
    }
}

inline fun com.intellij.openapi.diagnostic.Logger.trace(@NonNls lazyMessage: () -> String) {
    if (isTraceEnabled) {
        trace(lazyMessage())
    }
}

/** Consider using [Result.getOrLogException] for more straight-forward API instead. */
inline fun <T> com.intellij.openapi.diagnostic.Logger.runAndLogException(runnable: () -> T): T? {
    return runCatching {
        runnable()
    }.getOrLogException(this)
}

fun <T> Result<T>.getOrLogException(logger: com.intellij.openapi.diagnostic.Logger): T? {
    return onFailure { e ->
        if (e is ProcessCanceledException || e is CancellationException) {
            throw e
        } else {
            logger.error(e)
        }
    }.getOrNull()
}
