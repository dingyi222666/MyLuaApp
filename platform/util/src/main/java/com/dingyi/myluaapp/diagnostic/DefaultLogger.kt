// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.util.ExceptionUtil
import java.util.stream.Collectors
import java.util.stream.Stream

open class DefaultLogger(category: String) : Logger() {

    override val isDebugEnabled: Boolean
        get() = false
    override val isTraceEnabled: Boolean
        get() = isDebugEnabled

    override fun debug(message: String) {}
    override fun debug(t: Throwable?) {}
    override fun debug(message: String, t: Throwable?) {}
    override fun info(message: String) {}
    override fun info(message: String, t: Throwable?) {}
    override fun warn(message: String, t: Throwable?) {
        var t = t
        t = ensureNotControlFlow(t)
        System.err.println("WARN: $message")
        t.printStackTrace(System.err)
    }

    override fun error(message: String, t: Throwable?, vararg details: String) {
        var message = message
        var t = t
        t = ensureNotControlFlow(t)
        message += attachmentsToString(t)
        dumpExceptionsToStderr(message, t, *details)
        throw AssertionError(message, t)
    }

    override fun setLevel(level: LogLevel) {}

    companion object {
        private var ourMirrorToStderr = true
        fun dumpExceptionsToStderr(message: String, t: Throwable?, vararg details: String) {
            if (shouldDumpExceptionToStderr()) {
                System.err.println("ERROR: " + message + detailsToString(*details))
                t?.printStackTrace(System.err)
            }
        }

        fun detailsToString(vararg details: String): String {
            return if (details.size > 0) """
     
     Details:
     ${java.lang.String.join("\n", *details)}
     """.trimIndent() else ""
        }

        fun attachmentsToString(t: Throwable?): String {
            if (t != null) {
                val prefix = "\n\nAttachments:\n"
                val attachments =
                    ExceptionUtil.findCauseAndSuppressed(t, ExceptionWithAttachments::class.java)
                        .stream()
                        .flatMap { e: ExceptionWithAttachments -> Stream.of(*e.attachments) }
                        .map(ATTACHMENT_TO_STRING)
                        .collect(Collectors.joining("\n----\n", prefix, ""))
                if (attachments != prefix) {
                    return attachments
                }
            }
            return ""
        }

        fun shouldDumpExceptionToStderr(): Boolean {
            return ourMirrorToStderr
        }

        fun disableStderrDumping(parentDisposable: Disposable) {
            val prev = ourMirrorToStderr
            ourMirrorToStderr = false
            Disposer.register(parentDisposable) { ourMirrorToStderr = prev }
        }
    }
}