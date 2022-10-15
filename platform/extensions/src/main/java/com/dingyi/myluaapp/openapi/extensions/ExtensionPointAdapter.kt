// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

/**
 * @see ExtensionPointChangeListener
 */
abstract class ExtensionPointAdapter<T> : ExtensionPointListener<T> {
    override fun extensionAdded(extension: T, pluginDescriptor: PluginDescriptor) {
        extensionListChanged()
    }

    override fun extensionRemoved(extension: T, pluginDescriptor: PluginDescriptor) {
        extensionListChanged()
    }

    /**
     * Fired when extensions are added or removed in the EP.
     */
    abstract fun extensionListChanged()
}+