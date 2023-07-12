package com.dingyi.MyLuaApp.openapi.service

import com.dingyi.MyLuaApp.openapi.annotation.ServiceScope

interface ServiceRegistry {

    /**
     * Parent registry, or null if this is the root registry.
     */
    val parent: MutableServiceRegistry?

    /**
     * Root registry.
     */
    val root: MutableServiceRegistry


    /**
     * Scope of this registry.
     */
    val scope: ServiceScope

    /**
     * Returns a service registered in this registry or in one of its parents.
     */
    fun <T> getService(serviceClass: Class<T>): T?


    /**
     * Returns a service provider registered in this registry or in one of its parents.
     */
    fun <T> getProvider(providerClass: Class<T>): T?

    /**
     * Disposes this registry and all its services if the service extends [Disposable].
     */
    fun dispose()
}