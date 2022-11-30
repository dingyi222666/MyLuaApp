package com.dingyi.myluaapp.openapi.dsl.plugin.extension

import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder

class ExtensionPointsDslBuilder internal constructor() {


    val allExtensionPointBuilder = mutableListOf<ExtensionPointBuilder>()

    fun extensionPoint(
        name: String,
        block: ExtensionPointBuilder.() -> Unit
    ): ExtensionPointBuilder {
        val builder = ExtensionPointBuilder(name).also(block)
        allExtensionPointBuilder.add(builder)
        return builder
    }

}


class ExtensionPointBuilder internal constructor(var name: String) {


    var isQualifiedName = false

    enum class Area {
        PROJECT, APPLICATION
    }

    var beanClass: Any? = null

    var area = Area.PROJECT


    var interfaceClass: Any? = null



}


class ExtensionsDslBuilder internal constructor(var pluginId: String = "com.myluaapp") {


    val allImplementation = mutableMapOf<String, MutableList<ExtensionsImplementationBuilder>>()

    infix fun String.implementation(block: ExtensionsImplementationBuilder.() -> Unit): ExtensionsImplementationBuilder {
        val builder = ExtensionsImplementationBuilder().also(block)
        val old = allImplementation[this] ?: mutableListOf()
        old.add(builder)
        allImplementation[this] = old
        return builder
    }



}

class ExtensionsImplementationBuilder internal constructor() {
    var implementation: String? = null

    val beanImplementKeys = mutableMapOf<String, Any>()

    infix fun String.withKeyImplementation(implementationClass: Any) {
        beanImplementKeys[this] = implementationClass
    }


}


fun extensions(pluginId: String, block: ExtensionsDslBuilder.() -> Unit): ExtensionsDslBuilder {
    return ExtensionsDslBuilder(pluginId).also(block)
}

val func2 = ::extensions

fun PluginDslBuilder.extensions(pluginId: String, block: ExtensionsDslBuilder.() -> Unit) {
    if (!isInitializedExtensions()) {
        this.extensions = mutableListOf()
    }
    extensions.add(func2(pluginId, block))
}

fun extensionPoints(block: ExtensionPointsDslBuilder.() -> Unit): ExtensionPointsDslBuilder {
    return ExtensionPointsDslBuilder().also(block)
}

val func = ::extensionPoints

fun PluginDslBuilder.extensionPoints(block: ExtensionPointsDslBuilder.() -> Unit) {
    extensionPoints = func(block)
}