// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly

interface ExtensionsArea {
    @TestOnly
    fun registerExtensionPoint(
        @NonNls extensionPointName: String,
        extensionPointBeanClass: String,
        kind: ExtensionPoint.Kind,
        isDynamic: Boolean
    )

    @TestOnly
    @Deprecated("Use {@link #registerExtensionPoint(String, String, ExtensionPoint.Kind, boolean)}")
    fun registerExtensionPoint(
        @NonNls extensionPointName: String,
        extensionPointBeanClass: String,
        kind: ExtensionPoint.Kind
    ) {
        registerExtensionPoint(extensionPointName, extensionPointBeanClass, kind, false)
    }

    @TestOnly
    fun unregisterExtensionPoint(@NonNls extensionPointName: String)
    fun hasExtensionPoint(@NonNls extensionPointName: String): Boolean
    fun hasExtensionPoint(extensionPointName: ExtensionPointName<*>): Boolean
    fun <T:Any> getExtensionPoint(@NonNls extensionPointName: String): ExtensionPoint<T>
    fun <T:Any> getExtensionPointIfRegistered(extensionPointName: String): ExtensionPoint<T>?

    fun <T : Any> getExtensionPoint(extensionPointName: ExtensionPointName<T>): ExtensionPointImpl<T>
}