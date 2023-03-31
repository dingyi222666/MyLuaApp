package com.dingyi.myluaapp.openapi.extensions

/**
 * @see ExtensionPointChangeListener
 */
abstract class ExtensionPointAdapter<T> : ExtensionPointListener<T> {
    override fun extensionAdded(extension: T) {
        extensionListChanged()
    }

    override fun extensionRemoved(extension: T) {
        extensionListChanged()
    }

    /**
     * Fired when extensions are added or removed in the EP.
     */
    abstract fun extensionListChanged()
}