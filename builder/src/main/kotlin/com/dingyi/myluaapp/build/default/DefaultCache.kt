package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Cache

class DefaultCache:Cache {
    private val map = mutableMapOf<String,Any>()
    override fun <T> getCache(key: String): T {
        return map[key] as T
    }

    override fun putCache(key: String, value: Any) {
        map[key] = value
    }
}