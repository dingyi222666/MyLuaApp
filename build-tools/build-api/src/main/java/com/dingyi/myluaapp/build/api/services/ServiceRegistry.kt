package com.dingyi.myluaapp.build.api.services

import java.lang.reflect.Type

interface ServiceRegistry {
    /**
     * Locates the service of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
     * @return The service instance. Never returns null.
     * @throws UnknownServiceException When there is no service of the given type available.
     * @throws ServiceLookupException On failure to lookup the specified service.
    </T> */
    operator fun <T> get(serviceType: Class<T>): T

    /**
     * Locates all services of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
     * @throws ServiceLookupException On failure to lookup the specified service.
    </T> */
    fun <T> getAll(serviceType: Class<T>): List<T>

    /**
     * Locates the service of the given type.
     *
     * @param serviceType The service type.
     * @return The service instance. Never returns null.
     * @throws UnknownServiceException When there is no service of the given type available.
     * @throws ServiceLookupException On failure to lookup the specified service.
     */
    operator fun get(serviceType: Type): Any
}