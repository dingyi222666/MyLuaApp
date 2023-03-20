package com.dingyi.myluaapp.openapi.service

interface ServiceProvider {
    fun <T> getService(serviceClass: Class<T>): T?
}