package com.dingyi.myluaapp.openapi.service

interface ServiceManager {

    fun <T> getService(serviceClass: Class<T>): T

    fun <T> getService(serviceClass: Class<T>, name: String): T

    fun createNewServiceManager(): ServiceManager

}