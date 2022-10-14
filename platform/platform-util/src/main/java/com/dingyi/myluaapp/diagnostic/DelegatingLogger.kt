// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

abstract class DelegatingLogger<T : Logger> protected constructor(protected val myDelegate: T) :
    Logger() {


    override val isTraceEnabled = myDelegate.isTraceEnabled


    override fun trace(message: String) {
        myDelegate.trace(message)
    }

    override fun trace(t: Throwable?) {
        myDelegate.trace(t)
    }

    override val isDebugEnabled = myDelegate.isDebugEnabled


    override fun debug(message: String) {
        myDelegate.debug(message)
    }

    override fun debug(t: Throwable?) {
        myDelegate.debug(t)
    }

    override fun debug(message: String, t: Throwable?) {
        myDelegate.debug(message, t)
    }

    override fun info(message: String) {
        myDelegate.info(message)
    }

    override fun info(message: String, t: Throwable?) {
        myDelegate.info(message, t)
    }

    override fun warn(message: String, t: Throwable?) {
        myDelegate.warn(message, t)
    }

    override fun error(message: String, t: Throwable?, vararg details: String) {
        myDelegate.error(message, t, *details)
    }

    override fun setLevel(level: LogLevel) {
        myDelegate.setLevel(level)
    }
}