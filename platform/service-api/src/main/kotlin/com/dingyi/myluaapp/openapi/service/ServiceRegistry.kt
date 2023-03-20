package com.dingyi.myluaapp.openapi.service

import com.dingyi.myluaapp.openapi.annotation.ServiceScope

interface ServiceRegistry {

    val parent: ServiceRegistry?

    val root: ServiceRegistry

    val scope: ServiceScope

    fun registerService(service: Any)

    fun registerService(service: Any, serviceClass: Class<*>)

    fun <T> getService(serviceClass: Class<T>): T?

    fun <T> createService(serviceClass: Class<T>): T?

    fun createService(serviceClass: Class<*>, injectClassLoader: ClassLoader): Any?

    fun addProvider(provider: ServiceProvider)

    fun <T> getProvider(providerClass: Class<T>): T?

    fun dispose()


}
