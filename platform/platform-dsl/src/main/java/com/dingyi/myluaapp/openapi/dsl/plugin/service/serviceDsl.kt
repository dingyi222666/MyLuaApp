package com.dingyi.myluaapp.openapi.dsl.plugin.service

import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder

open class ServiceDslBuilder internal constructor() {

    val applicationLevelServices = mutableMapOf<String, ServiceImplBuilder>()

    val projectLevelServices = mutableMapOf<String, ServiceImplBuilder>()

    private fun toString(target:Any):String {
        return when (target) {
            is Class<*> -> target.name
            else -> target.toString()
        }
    }

    fun application(interfaceClass: Any): ServiceImplBuilder {
        if (interfaceClass is String || interfaceClass is Class<*>) {
            return ServiceImplBuilder().also {
                applicationLevelServices[toString(interfaceClass)] = it
            }
        }
        error("")
    }


    fun project(interfaceClass: Any): ServiceImplBuilder {
        if (interfaceClass is String || interfaceClass is Class<*>) {
            return ServiceImplBuilder().also {
                projectLevelServices[toString(interfaceClass)] = it
            }
        }
        error("")
    }


    inner class ServiceImplBuilder internal constructor() {

        lateinit var impClass:String

        var preload = PreloadMode.FALSE

        infix fun impl(clazz: Class<*>) {
            impClass = clazz.name
        }

        infix fun impl(clazz: String) {
             impClass = clazz
        }
    }
}

enum class PreloadMode {
    TRUE, FALSE, AWAIT
}

fun services(block: ServiceDslBuilder.() -> Unit): ServiceDslBuilder {
    return ServiceDslBuilder().also(block)
}

val func = ::services

fun PluginDslBuilder.services(block: ServiceDslBuilder.() -> Unit) {
    services = func(block)
}
