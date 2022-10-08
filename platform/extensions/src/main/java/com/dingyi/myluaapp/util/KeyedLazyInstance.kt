package com.dingyi.myluaapp.util


interface KeyedLazyInstance<T> {
    val key: String
    val instance: T
}