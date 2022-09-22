package com.dingyi.myluaapp.openapi

interface Disposable {
    fun dispose()
    interface Parent : Disposable {
        fun beforeTreeDispose()
    }
}
