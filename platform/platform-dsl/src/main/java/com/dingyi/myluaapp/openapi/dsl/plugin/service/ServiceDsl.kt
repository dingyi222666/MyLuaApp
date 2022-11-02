package com.dingyi.myluaapp.openapi.dsl.plugin.service

import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder

open class ServiceDslBuilder internal constructor() {

    val applicationLevelServices = mutableMapOf<Any, Any>()

    val projectLevelServices = mutableMapOf<Any, Any>()

    fun application(interfaceClass: Any): ServiceImplBuilder {
        if (interfaceClass is String || interfaceClass is Class<*>) {
            return ServiceImplBuilder().call {
                applicationLevelServices[interfaceClass] = it
            }
        }
        error("")
    }


    fun project(interfaceClass: Any): ServiceImplBuilder {
        if (interfaceClass is String || interfaceClass is Class<*>) {
            return ServiceImplBuilder().call {
                projectLevelServices[interfaceClass] = it
            }
        }
        error("")
    }


    inner class ServiceImplBuilder internal constructor() {

        private lateinit var call: (Any) -> Unit

        infix fun impl(clazz: Class<*>) {
            call(clazz)
        }

        infix fun impl(clazz: String) {
            call(clazz)
        }

        internal fun call(block: (Any) -> Unit): ServiceImplBuilder {
            call = block
            return this
        }
    }
}


fun services(block: ServiceDslBuilder.() -> Unit): ServiceDslBuilder {
    return ServiceDslBuilder().also(block)
}

val func = ::services

fun PluginDslBuilder.services(block: ServiceDslBuilder.() -> Unit) {
    services = func(block)
}
