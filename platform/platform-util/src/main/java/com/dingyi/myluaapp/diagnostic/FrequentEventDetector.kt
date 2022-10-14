/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.diagnostic

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.util.ExceptionUtil
import com.intellij.util.containers.FixedHashMap
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author peter
 */
class FrequentEventDetector @JvmOverloads constructor(
    private val myEventCountThreshold: Int,
    private val myTimeSpanMs: Int,
    private val myLevel: Level = Level.INFO
) {
    enum class Level {
        INFO, WARN, ERROR
    }

    private var myStartedCounting = System.currentTimeMillis()
    private val myEventsPosted = AtomicInteger()
    private val myLastTraceId = AtomicInteger()
    private val myRecentTraces: MutableMap<String, Int> = FixedHashMap(50)

    /**
     * @return an error message to be logged, if the current event is a part of a "frequent"-series, null otherwise
     */
    private fun getMessageOnEvent(event: Any): String? {
        return if (disableRequests.get() == 0) {
            manyEventsHappenedInSmallTimeSpan(event)
        } else null
    }

    private fun manyEventsHappenedInSmallTimeSpan(event: Any): String? {
        val eventsPosted = myEventsPosted.incrementAndGet()
        var shouldLog = false
        if (eventsPosted > myEventCountThreshold) {
            synchronized(myEventsPosted) {
                if (myEventsPosted.get() > myEventCountThreshold) {
                    val timeNow = System.currentTimeMillis()
                    shouldLog = timeNow - myStartedCounting < myTimeSpanMs
                    myEventsPosted.set(0)
                    myStartedCounting = timeNow
                }
            }
        }
        return if (shouldLog) generateMessage(event, eventsPosted) else null
    }

    @NonNls
    private fun generateMessage(event: Any, eventsPosted: Int): String {
        val trace = ExceptionUtil.getThrowableText(Throwable())
        var logTrace: Boolean
        var traceId: Int
        synchronized(myEventsPosted) {
            val existingTraceId = myRecentTraces[trace]
            logTrace = existingTraceId == null
            if (logTrace) {
                myRecentTraces.put(trace, myLastTraceId.incrementAndGet().also { traceId = it })
            } else {
                traceId = existingTraceId!!
            }
        }
        return ("Too many events posted (" + eventsPosted + ")"
                + " #" + traceId + ". Event: '" + event + "'"
                + if (logTrace) """
     
     $trace
     """.trimIndent() else "")
    }

    fun logMessage(message: String) {
        if (myLevel == Level.INFO) {
            LOG.info(message)
        } else if (myLevel == Level.WARN) {
            LOG.warn(message)
        } else {
            LOG.error(message)
        }
    }

    /**
     * Logs a message if the given event is part of a "frequent" series. To just return the message without logging, use [.getMessageOnEvent]
     */
    fun eventHappened(event: Any) {
        val message = getMessageOnEvent(event)
        message?.let { logMessage(it) }
    }

    companion object {
        private val LOG = Logger.getInstance(
            FrequentEventDetector::class.java
        )
        private val disableRequests = AtomicInteger()
        @TestOnly
        fun disableUntil(reenable: Disposable) {
            disableRequests.incrementAndGet()
            Disposer.register(reenable) { disableRequests.decrementAndGet() }
        }
    }
}