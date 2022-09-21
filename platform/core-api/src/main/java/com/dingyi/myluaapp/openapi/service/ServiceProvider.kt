package com.dingyi.myluaapp.openapi.service

import androidx.annotation.Nullable

interface ServiceProvider {

    /**
     * Locates a service instance of the given type. Returns null if this provider does not provide a service of this type.
     */
    @Nullable
    fun getService(serviceType: Class<*>): Service?


    fun getAll(serviceType: Class<*>): Iterator<Service>

    companion object EmptyServiceProvider : ServiceProvider {
        override fun getService(serviceType: Class<*>): Service? {
            return null
        }

        override fun getAll(serviceType: Class<*>): Iterator<Service> = EmptyIterator

    }

    object EmptyIterator : Iterator<Service> {
        override fun hasNext(): Boolean {
            return false
        }

        override fun next(): Service {
            return object : Service {
                override fun get(): Any {
                    return Any()
                }

                override fun getDisplayName(): String {
                    return "EmptyIterator"
                }

            }
        }

    }
}