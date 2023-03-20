package com.dingyi.myluaapp.openapi.service

internal class AnyLazyServiceProvider : ServiceProvider {
    private val services = mutableMapOf<Class<*>, Any>()

    override fun <T> getService(serviceClass: Class<T>): T? {
        return services[serviceClass] as? T
    }

    fun <T : Any> registerService(serviceClass: Class<T>, service: T) {
        services[serviceClass] = service
    }
}