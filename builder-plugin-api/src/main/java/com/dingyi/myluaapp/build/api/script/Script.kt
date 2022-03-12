package com.dingyi.myluaapp.build.api.script

interface Script {
    fun get(key: String): Any?

    fun run()


    fun close()

    fun put(key: String, value: Any?)

    fun getPath(): String

    fun getName(): String

}