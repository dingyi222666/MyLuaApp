package com.dingyi.myluaapp.build.api

interface Action<T> {
    fun execute(t: T)
}