package com.dingyi.myluaapp.builder.api.script

interface Script {
    fun get(key:String):Any?

    fun run()

    fun invoke(vararg args:Any):Array<Any?>?
}