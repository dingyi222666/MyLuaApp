package com.dingyi.myluaapp.build.api.service

interface ServiceRepository:Service {
    fun loadService(className: String): Service

    fun loadService(className: String, dexPath: String): Service

    fun init()

    fun getServices(): List<Service>

    fun shutdown()
}