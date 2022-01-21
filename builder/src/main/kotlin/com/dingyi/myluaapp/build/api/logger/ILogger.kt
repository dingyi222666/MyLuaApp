package com.dingyi.myluaapp.build.api.logger

interface ILogger {

    fun waring(string: String)

    fun info(string: String)

    fun debug(string: String)

    fun error(string: String)
}