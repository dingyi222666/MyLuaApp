package com.dingyi.myluaapp.openapi.service

import com.dingyi.myluaapp.openapi.extensions.AreaInstance
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.UserDataHolder


interface ServiceRegistry: UserDataHolder, Disposable, AreaInstance{
    /**
     * Locates the service of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
     * @return The service instance. Never returns null.
    </T> */
    operator fun <T> get(serviceType: Class<T>): T


    /**
     * Locates the service of the given type, returning null if no such service.
     *
     * @param serviceType The service type.
     * @return The service instance. Returns `null` if no such service exists.

     */
    fun find(serviceType: Class<*>): Any?

    /**
     * Locates the service of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
     * @return The service instance. Never returns null.
    </T> */
    operator fun <T> get(serviceType: String): T


    /**
     * Locates the service of the given type, returning null if no such service.
     *
     * @param serviceType The service type.
     * @return The service instance. Returns `null` if no such service exists.

     */
    fun find(serviceType: String): Any?

    /**
     * Locates all services of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
    </T> */

    fun <T> getAll(serviceType: Class<T>): List<T>


    fun asRegistration(): ServiceRegistration



}