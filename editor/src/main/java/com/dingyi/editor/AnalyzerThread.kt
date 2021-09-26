package com.dingyi.editor

import java.util.*
import kotlin.concurrent.thread

/**
 * @author: dingyi
 * @date: 2021/9/20 20:09
 * @description:
 **/
abstract class AnalyzerThread() {

    private var lookObject: Any? = null

    private var analyzerObject: Any? = null

    private var runThread: Thread? = null

    private var runSuccessFlag = false


    fun getOrLock(nowObject: Any): Any? {
        if (nowObject.toString() == nowObject.toString()) {
            return if (runSuccessFlag) {
                analyzerObject
            } else {
                //lock
                while (true) {
                    if (analyzerObject == true) {
                        break
                    }
                }
                analyzerObject
            }
        } else {
            runThread?.interrupt()
            run(nowObject)
            return null
        }

        //fail

    }

    fun interrupt() {
        kotlin.runCatching {
            runThread?.interrupt()
        }
    }

    fun run(nowObject: Any) {
        runThread = thread(start = true) {
            runSuccessFlag = false
            analyzerObject = analyze(nowObject)
            runSuccessFlag = true
        }
    }

    abstract fun analyze(nowObject: Any): Any

}