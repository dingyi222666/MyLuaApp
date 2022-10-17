package com.dingyi.myluaapp.openapi.service.internal

import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolder

internal class SimpleUserDataHolder : UserDataHolder {

    private val map = mutableMapOf<Key<*>, Any?>()

    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return  map[key] as T?
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        map.put(key, value)
    }
}