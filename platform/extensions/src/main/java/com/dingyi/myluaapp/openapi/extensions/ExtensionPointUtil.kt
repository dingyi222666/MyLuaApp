// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import com.dingyi.myluaapp.util.KeyedLazyInstance
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.ClearableLazyValue
import com.intellij.openapi.util.Disposer
import java.util.function.Predicate

object ExtensionPointUtil {
    fun <V : ClearableLazyValue<*>> dropLazyValueOnChange(
        lazyValue: V,
        extensionPointName: ExtensionPointName<*>,
        parentDisposable: Disposable
    ): V {
        extensionPointName.addChangeListener({ lazyValue.drop() }, parentDisposable)
        return lazyValue
    }

    fun <T : Any> createExtensionDisposable(
        extensionObject: T,
        extensionPointName: ExtensionPointName<T>
    ): Disposable {
        return createExtensionDisposable(extensionObject, extensionPointName.point)
    }

    fun <T> createExtensionDisposable(
        extensionObject: T,
        extensionPoint: ExtensionPoint<T>
    ): Disposable {
        return createExtensionDisposable(
            extensionObject,
            extensionPoint
        ) { removed: T -> removed === extensionObject }
    }

    fun <T, U> createExtensionDisposable(
        extensionObject: T,
        extensionPoint: ExtensionPoint<U>,
        removePredicate: Predicate<in U>
    ): Disposable {
        val disposable = createDisposable(extensionObject, extensionPoint)
        extensionPoint.addExtensionPointListener(object : ExtensionPointListener<U> {
            override fun extensionRemoved(extension: U, pluginDescriptor: PluginDescriptor) {
                if (removePredicate.test(extension)) {
                    Disposer.dispose(disposable)
                }
            }
        }, false, disposable)
        return disposable
    }

    fun <T> createKeyedExtensionDisposable(
        extensionObject: T,
        extensionPoint: ExtensionPoint<KeyedLazyInstance<T>>
    ): Disposable {
        val disposable = createDisposable(extensionObject, extensionPoint)
        extensionPoint.addExtensionPointListener(object : ExtensionPointListener<KeyedLazyInstance<T>> {
            override fun extensionRemoved(
                extension: KeyedLazyInstance<T>,
                pluginDescriptor: PluginDescriptor
            ) {
                if (extensionObject === extension.instance) {
                    Disposer.dispose(disposable)
                }
            }
        }, false, disposable)
        return disposable
    }

    private fun <T> createDisposable(
        extensionObject: T,
        extensionPoint: ExtensionPoint<*>
    ): Disposable {
        val disposable = Disposer.newDisposable("Disposable for [$extensionObject]")
        val manager = (extensionPoint as ExtensionPointImpl<*>).componentManager
        Disposer.register(manager, disposable)
        return disposable
    }
}