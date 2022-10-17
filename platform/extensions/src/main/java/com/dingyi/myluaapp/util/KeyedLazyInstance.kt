package com.dingyi.myluaapp.util


interface KeyedLazyInstance<T> {
    val key: String
    fun getInstance():T
}