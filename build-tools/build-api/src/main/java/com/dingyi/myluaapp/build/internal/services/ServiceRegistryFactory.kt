package com.dingyi.myluaapp.build.internal.services

import com.dingyi.myluaapp.build.api.services.ServiceRegistry

/**
 * A hierarchical service registry.
 */
interface ServiceRegistryFactory {
    /**
     * Creates the services for the given domain object.
     *
     * @param domainObject The domain object.
     * @return The registry containing the services for the domain object.
     */
    fun createFor(domainObject: Any): ServiceRegistry
}