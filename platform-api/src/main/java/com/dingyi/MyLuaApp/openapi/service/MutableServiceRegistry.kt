package com.dingyi.MyLuaApp.openapi.service


import com.dingyi.MyLuaApp.openapi.annotation.ServiceScope

interface MutableServiceRegistry : ServiceRegistry {


    /**
     * Registers a service in this registry. The class type of the service is used as a key.
     */
    fun registerService(service: Any)

    /**
     * Registers a service in this registry. The specified class is used as a key. This method is useful when a service
     */
    fun registerService(service: Any, serviceClass: Class<*>)


    /**
     * Try to create a service registered in this registry or in one of its parents.
     */
    fun <T> createService(serviceClass: Class<T>): T?

    /**
     * Try use [injectClassLoader] to create a service registered in this registry or in one of its parents.
     */
    fun createService(serviceClass: Class<*>, injectClassLoader: ClassLoader): Any?

    /**
     * Add a service provider to this registry.
     */
    fun addProvider(provider: ServiceProvider)

}