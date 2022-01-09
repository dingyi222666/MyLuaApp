package com.dingyi.myluaapp.builder.api

interface Action<T> {

    fun execute(t: T)


}