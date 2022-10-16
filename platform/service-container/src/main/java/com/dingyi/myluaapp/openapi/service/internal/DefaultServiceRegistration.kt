package com.dingyi.myluaapp.openapi.service.internal

import com.dingyi.myluaapp.openapi.service.ServiceRegistration

internal class DefaultServiceRegistration(private val defaultServiceRegistry: DefaultServiceRegistry) :
    ServiceRegistration {
    override fun <T : Any> add(serviceType: Class<T>, serviceInstance: T) {
        defaultServiceRegistry.add(serviceType,serviceInstance)
    }

    override fun add(serviceType: Class<*>) {
        defaultServiceRegistry.add(serviceType)
    }

    override fun addProvider(provider: Any) {
       defaultServiceRegistry.addProvider(provider)
    }

}
