package com.dingyi.MyLuaApp.openapi.service

interface ServiceProvider {
    fun <T> getService(serviceClass: Class<T>): T?
}