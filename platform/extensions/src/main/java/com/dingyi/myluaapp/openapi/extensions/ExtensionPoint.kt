package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.util.Disposable


/**
 *
 */
interface ExtensionPoint<T : Any?> {

    fun registerExtension(extension: T): Disposable

    val extensionList: List<T>

    val size: Int

    /**
     * Unregisters an extension of the specified type.
     *
     *
     * Please note that you can deregister service specifying empty implementation class.
     *
     *

     */
    fun unregisterExtension(extensionClass: Class<out T>)

    /**
     * Unregisters extensions for which the specified predicate returns false.
     *
     *
     */
    fun unregisterExtensions(
        extensionClassNameFilter: (String) -> Boolean,
        stopAfterFirstMatch: Boolean
    ): Boolean

    fun addExtensionPointListener(
        listener: ExtensionPointListener<T>,
        parentDisposable: Disposable?
    )

    /**
     * Consider using [ExtensionPointName.addChangeListener]
     */
    fun addChangeListener(listener: Runnable, parentDisposable: Disposable?)
    fun removeExtensionPointListener(extensionPointListener: ExtensionPointListener<T>)


}