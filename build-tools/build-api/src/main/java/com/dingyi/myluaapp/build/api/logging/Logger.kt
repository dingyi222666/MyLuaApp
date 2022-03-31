package com.dingyi.myluaapp.build.api.logging

interface Logger {

    fun w(message: String)

    fun e(message: String)

    fun e(message: String, throwable: Throwable)


    fun i(message: String)

    fun v(message: String)


}