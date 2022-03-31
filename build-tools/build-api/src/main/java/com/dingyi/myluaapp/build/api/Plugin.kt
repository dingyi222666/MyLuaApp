package com.dingyi.myluaapp.build.api

interface Plugin<T> {

    fun apply(target: T)
}