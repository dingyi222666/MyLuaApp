package com.dingyi.myluaapp.openapi.service

import com.dingyi.myluaapp.openapi.extensions.AreaInstance
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.util.messages.MessageBus
import com.dingyi.myluaapp.util.messages.MessageBusOwner
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.UserDataHolder


interface ServiceRegistry : UserDataHolder, Disposable, AreaInstance,MessageBusOwner {
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


    /**
     * try Load class
     */
    fun <T> instantiateClass(serviceType: Class<T>, pluginDescriptor: PluginDescriptor): T

    fun instantiateClass(serviceType: String, pluginDescriptor: PluginDescriptor): Any

    fun asRegistration(): ServiceRegistration

    val messageBus:MessageBus


}