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

    val withAttributes = mutableMapOf<String, String>()

    enum class Area {
        PROJECT, APPLICATION
    }

    var beanClass: Any = ""

    var area = Area.PROJECT


    var interfaceClass: Any = ""

    infix fun String.withImplements(implement: String) {
        withAttributes[this] = implement
    }
}


class ExtensionsDslBuilder internal constructor(var pluginId: String = "com.myluaapp") {


    val allImplementation = mutableListOf<ExtensionPointBuilder>()

    infix fun String.implementation(block: ExtensionsImplementationBuilder.() -> Unit): ExtensionsImplementationBuilder {
        val builder = ExtensionsImplementationBuilder().also(block)
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