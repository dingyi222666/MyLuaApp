package com.dingyi.myluaapp.build.api

interface BuildResult {

    fun getResult():Any

    fun getError():Throwable?

    fun isSuccessful():Boolean
}