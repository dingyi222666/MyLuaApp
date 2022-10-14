package com.dingyi.myluaapp.openapi.components

inline fun <reified T> ComponentManager.getService():T {
    return getService(T::class.java)
}