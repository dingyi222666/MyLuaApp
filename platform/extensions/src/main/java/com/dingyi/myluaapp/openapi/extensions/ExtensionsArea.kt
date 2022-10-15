// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly

interface ExtensionsArea {
    @TestOnly
    fun registerExtensionPoint(
        extensionPointName: @NonNls String,
        extensionPointBeanClass: String,
        kind: ExtensionPoint.Kind,
        isDynamic: Boolean
    )

    @TestOnly
    @Deprecated("Use {@link #registerExtensionPoint(String, String, ExtensionPoint.Kind, boolean)}")
    fun registerExtensionPoint(
        extensionPointName: @NonNls String,
        extensionPointBeanClass: String,
        kind: ExtensionPoint.Kind
    ) {
        registerExtensionPoint(extensionPointName, extensionPointBeanClass, kind, false)
    }

    @TestOnly
    fun unregisterExtensionPoint(extensionPointName: @NonNls String)
    fun hasExtensionPoint(extensionPointName: @NonNls String): Boolean
    fun hasExtensionPoint(extensionPointName: ExtensionPointName<*>): Boolean
    fun <T:Any> getExtensionPoint(extensionPointName: @NonNls String): ExtensionPoint<T>
    fun <T:Any> getExtensionPointIfRegistered(extensionPointName: String): ExtensionPoint<T>?
    fun <T:Any> getExtensionPoint(extensionPointName: ExtensionPointName<T>): ExtensionPoint<T>
}