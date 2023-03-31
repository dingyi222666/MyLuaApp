package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl


interface ExtensionsArea {

    fun registerExtensionPoint(
        extensionPointName: String,
        extensionPointClass: Class<*>
    )

    fun unregisterExtensionPoint(extensionPointName: String)
    fun hasExtensionPoint(extensionPointName: String): Boolean
    fun hasExtensionPoint(extensionPointName: ExtensionPointName<*>): Boolean
    fun <T : Any> getExtensionPoint(extensionPointName: String): ExtensionPoint<T>
    fun <T : Any> getExtensionPointIfRegistered(extensionPointName: String): ExtensionPoint<T>?

    fun <T : Any> getExtensionPoint(extensionPointName: ExtensionPointName<T>): ExtensionPointImpl<T>
}