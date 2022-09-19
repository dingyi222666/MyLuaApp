package com.dingyi.myluaapp.openapi.service

interface ServiceProvider {

    fun <T> getService(serviceClass: Class<T>): T

    fun <T> getService(serviceClass: Class<T>, name: String): T



}