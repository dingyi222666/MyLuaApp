package com.dingyi.editor

import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.concurrent.thread
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/9/20 20:09
 * @description:
 **/
abstract class AnalyzerThread() {

    private var lookObject:Any? = Any()

    private var analyzerObject: Any? = null

    private var runSuccessFlag = false

    private val threadPool = Executors.newCachedThreadPool()

    private var nowRunFuture:Future<*>? = null

    var waitRefreshCallback = {

    }

    fun pushObject(nowObject: Any){
        if (nowObject.hashCode() != lookObject?.hashCode()) {
            nowRunFuture?.cancel(true)
            lookObject  = nowObject
            run()
        }

    }

    fun getOrNull():Any? {
        if (runSuccessFlag) {
            return analyzerObject
        }
        return null
    }

    fun interrupt() {
        runCatching {
            nowRunFuture?.cancel(true)
        }
    }

    fun shutdown() {
        threadPool.shutdownNow()
    }

    fun run() {
        nowRunFuture = threadPool.submit {
            runSuccessFlag = false
            analyzerObject = lookObject?.let { analyze(it) }
            waitRefreshCallback()
            runSuccessFlag = true
        }
    }

    abstract fun analyze(nowObject: Any): Any

}