package com.dingyi.myluaapp.util

interface KeyedLazyInstance<T> {
    fun getKey(): String
    fun getInstance(): T
}