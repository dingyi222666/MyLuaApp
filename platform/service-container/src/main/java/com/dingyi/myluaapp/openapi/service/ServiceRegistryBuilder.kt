package com.dingyi.myluaapp.openapi.service

import com.dingyi.myluaapp.openapi.service.internal.DefaultServiceRegistry


class ServiceRegistryBuilder private constructor() {
    private val parents = ArrayList<ServiceRegistry>()
    private val providers = ArrayList<Any>()
    private var displayName: String? = null

    fun displayName(displayName: String): ServiceRegistryBuilder {
        this.displayName = displayName
        return this
    }

    fun parent(parent: ServiceRegistry): ServiceRegistryBuilder {
        parents.add(parent)
        return this
    }

    fun provider(provider: Any): ServiceRegistryBuilder {
        providers.add(provider)
        return this
    }

    fun build(): ServiceRegistry {
        val registry = DefaultServiceRegistry(displayName, *parents.toTypedArray())
        for (provider in providers) {
            registry.addProvider(provider)
        }
        return registry
    }

    companion object {
        fun builder(): ServiceRegistryBuilder {
            return ServiceRegistryBuilder()
        }
    }
}