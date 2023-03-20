package com.dingyi.myluaapp.openapi.service

import androidx.annotation.CallSuper
import com.dingyi.myluaapp.openapi.annotation.Inject
import com.dingyi.myluaapp.openapi.annotation.ServiceScope
import com.dingyi.myluaapp.openapi.util.Disposable
import com.dingyi.myluaapp.openapi.util.Disposer
import java.lang.reflect.Constructor
import java.lang.reflect.Modifier


abstract class BaseServiceRegistry(parent: ServiceRegistry?) : ServiceRegistry {

    private var thisParent: ServiceRegistry? = parent

    private var thisRoot: ServiceRegistry = parent?.root ?: this

    private val services = mutableMapOf<Class<*>, Any>()

    private val providers = mutableListOf<ServiceProvider>()

    override val parent: ServiceRegistry?
        get() = thisParent

    override val root: ServiceRegistry
        get() = thisRoot

    init {
        services[ServiceRegistry::class.java] = this
    }


    override val scope: ServiceScope
        get() = ServiceScope.Application

    override fun registerService(service: Any) {
        val serviceRealClass = unpackClass(service)
        services[serviceRealClass] = service
    }

    override fun registerService(service: Any, serviceClass: Class<*>) {
        services[serviceClass] = service
    }


    override fun <T> getService(serviceClass: Class<T>): T? {
        return getServiceImpl(serviceClass) as? T
    }

    override fun <T> getProvider(providerClass: Class<T>): T? {
        providers.forEach {
            if (providerClass.isInstance(it)) {
                return it as T
            }
        }
        return null
    }

    override fun addProvider(provider: ServiceProvider) {
        providers.add(provider)
    }

    private fun getServiceImpl(serviceClass: Class<*>, scope: ServiceScope = this.scope): Any? {
        var service = services[serviceClass]
        if (service != null) {
            return service
        }
        service = loadServiceFromProvider(serviceClass)
        if (service != null) {
            services[serviceClass] = service
            return service
        }
        return when {
            scope == this.scope -> null
            scope == ServiceScope.Project && parent?.scope == ServiceScope.Project -> parent?.getService(
                serviceClass
            )

            scope == ServiceScope.Application -> root.getService(serviceClass)
            else -> null
        }
    }


    private fun loadServiceFromProvider(serviceClass: Class<*>): Any? {
        providers.forEach {
            val service = it.getService(serviceClass)
            if (service != null) {
                return service
            }
        }
        return null
    }

    override fun <T> createService(serviceClass: Class<T>): T? {
        return createService(serviceClass, serviceClass.classLoader) as? T
    }


    private fun buildInjectConstructor(
        constructor: Constructor<*>,
        injectClassLoader: ClassLoader
    ): Array<Any?> {
        val parameters = constructor.parameters
        val args = mutableListOf<Any?>()
        for (parameter in parameters) {
            val parameterType = parameter.type
            val parameterClass = parameterType as Class<*>
            val service = getService(parameterClass)
            val serviceInstance = service ?: createService(parameterClass, injectClassLoader)
            args.add(serviceInstance)
        }
        return args.toTypedArray()
    }

    private fun injectServiceFields(instance: Any, injectClassLoader: ClassLoader) {
        val fields = instance.javaClass.declaredFields
        val needInjectFields = fields.filter { it.isAnnotationPresent(Inject::class.java) }
        needInjectFields.forEach {
            var service = getServiceImpl(it.type as Class<*>)
            if (service == null) {
                service = createService(it.type as Class<*>, injectClassLoader)
            }
            it.isAccessible = true
            it.set(instance, service)
        }


    }

    override fun createService(serviceClass: Class<*>, injectClassLoader: ClassLoader): Any? {
        val service = services[serviceClass]
        if (service != null) {
            return service
        }
        val serviceRealClass = unpackClass(serviceClass)
        val constructor = selectInjectConstructor(serviceRealClass)
        val dependenciesInjectArgs = buildInjectConstructor(constructor, injectClassLoader)
        val instance = constructor.newInstance(*dependenciesInjectArgs)
        injectServiceFields(instance, injectClassLoader)
        registerService(instance, serviceRealClass)
        return instance
    }


    override fun dispose() {
        services.forEach {
            if (it.value is Disposable) {
                Disposer.dispose(it.value as Disposable)
            }
        }
        services.clear()
        thisParent = null
    }


}


internal fun selectInjectConstructor(serviceClass: Class<*>): Constructor<*> {
    val constructors = serviceClass.constructors
    val injectConstructors = constructors.filter {
        it.isAnnotationPresent(Inject::class.java)
    }
    if (injectConstructors.size > 1) {
        throw RuntimeException("Service class ${serviceClass.name} has more than one inject constructor")
    }
    if (injectConstructors.isEmpty()) {
        return constructors.firstOrNull()
            ?: throw RuntimeException("Service class ${serviceClass.name} has no constructor")
    }
    return injectConstructors.firstOrNull()
        ?: throw RuntimeException("Service class ${serviceClass.name} has no constructor")
}

internal fun unpackClass(service: Any): Class<*> {
    return when (service) {
        is Class<*> -> service
        else -> {
            var result = service.javaClass as Class<*>

            val superClass = result.superclass

            val interfaces = result.interfaces

            if (interfaces.size == 1) {
                result = interfaces.get(0) as Class<*>
            }

            if (Modifier.isAbstract(superClass.modifiers)) {
                result = superClass as Class<Any>
            }
            result
        }
    }
}