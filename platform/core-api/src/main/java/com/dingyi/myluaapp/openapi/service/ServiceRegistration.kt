package com.dingyi.myluaapp.openapi.service


/**
 * Allows services to be added to a registry.
 */
interface ServiceRegistration {
    /**
     * Adds a service to this registry. The given object is closed when the associated registry is closed.
     * @param serviceType The type to make this service visible as.
     * @param serviceInstance The service implementation.
     */
    fun <T:Any> add(serviceType: Class<T>, serviceInstance: T)

    /**
     * Adds a service to this registry. The implementation class should have a single public constructor, and this constructor can take services to be injected as parameters.
     *
     * @param serviceType The service implementation to make visible.
     */
    fun add(serviceType: Class<*>)

    /**
     * Adds a service provider bean to this registry. This provider may define factory and decorator methods. See [DefaultServiceRegistry] for details.
     */
    fun addProvider(provider: Any)
}