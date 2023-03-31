package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.extensions.ExtensionsArea

class ExtensionsAreaImpl : ExtensionsArea {

    private val myExtensionPoints = mutableMapOf<String, ExtensionPointImpl<*>>()

    override fun registerExtensionPoint(
        extensionPointName: String,
        extensionPointClass: Class<*>
    ) {
        val extensionPoint = ExtensionPointImpl(extensionPointClass as Class<Any>)
        myExtensionPoints[extensionPointName] = extensionPoint
    }


    override fun unregisterExtensionPoint(extensionPointName: String) {
        myExtensionPoints.remove(extensionPointName)
    }

    override fun hasExtensionPoint(extensionPointName: String): Boolean {
        return myExtensionPoints.containsKey(extensionPointName)
    }

    override fun hasExtensionPoint(extensionPointName: ExtensionPointName<*>): Boolean {
        return hasExtensionPoint(extensionPointName.name)
    }

    override fun <T : Any> getExtensionPoint(extensionPointName: String): ExtensionPointImpl<T> {
        return myExtensionPoints[extensionPointName] as ExtensionPointImpl<T>
    }

    override fun <T : Any> getExtensionPoint(extensionPointName: ExtensionPointName<T>): ExtensionPointImpl<T> {
        return getExtensionPoint(extensionPointName.name)
    }

    override fun <T : Any> getExtensionPointIfRegistered(extensionPointName: String): ExtensionPointImpl<T>? {
        return myExtensionPoints[extensionPointName] as ExtensionPointImpl<T>?
    }


}