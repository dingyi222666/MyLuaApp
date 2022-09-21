package com.dingyi.myluaapp.openapi.service


interface ServiceRegistry {
    /**
     * Locates the service of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
     * @return The service instance. Never returns null.
    </T> */
    operator fun <T> get(serviceType: Class<T>): T

    /**
     * Locates all services of the given type.
     *
     * @param serviceType The service type.
     * @param <T>         The service type.
    </T> */

    fun <T> getAll(serviceType: Class<T>): List<T>


    /**
     * Locates the service of the given type, returning null if no such service.
     *
     * @param serviceType The service type.
     * @return The service instance. Returns `null` if no such service exists.

     */


    fun find(serviceType: Class<*>): Any?


}