package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointListener
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.util.Disposable
import com.dingyi.myluaapp.openapi.util.Disposer

class ExtensionPointImpl<T : Any>(
    /*  private val name: ExtensionPointName<T>,*/
    private val beanClass: Class<T>,
) : ExtensionPoint<T> {

    private val myListeners = mutableListOf<ExtensionPointListener<T>>()
    private val myExtensions = mutableListOf<T>()
    override fun registerExtension(extension: T): Disposable {
        myExtensions.add(extension)
        dispatchChange(extension, Event.ADD)
        return Disposable {
            unregisterExtension(extension)
        }

    }


    override val extensionList: List<T>
        get() = myExtensions

    override val size: Int
        get() = myExtensions.size

    override fun unregisterExtensions(
        extensionClassNameFilter: (String) -> Boolean,
        stopAfterFirstMatch: Boolean
    ): Boolean {
        val targetExtensions = myExtensions.filter { extensionClassNameFilter(it.javaClass.name) }
        if (targetExtensions.isNotEmpty()) {
            targetExtensions.forEach {
                unregisterExtension(it)
            }
            return true
        }
        return false
    }

    override fun addChangeListener(listener: Runnable, parentDisposable: Disposable?) {
        addExtensionPointListener(object : ExtensionPointListener<T> {
            override fun extensionAdded(extension: T) {
                listener.run()
            }

            override fun extensionRemoved(extension: T) {
                listener.run()
            }
        }, parentDisposable)
    }

    override fun removeExtensionPointListener(extensionPointListener: ExtensionPointListener<T>) {
        myListeners.remove(extensionPointListener)
    }

    override fun addExtensionPointListener(
        listener: ExtensionPointListener<T>,
        parentDisposable: Disposable?
    ) {
        myListeners.add(listener)
        if (parentDisposable == null) return
        Disposer.register(parentDisposable) {
            myListeners.remove(listener)
        }
    }

    override fun unregisterExtension(extensionClass: Class<out T>) {
        val targetExtension = myExtensions.find { extensionClass.isInstance(it) }
        if (targetExtension != null) {
            unregisterExtension(targetExtension)
        }
    }

    private fun unregisterExtension(extension: T) {
        myExtensions.remove(extension)
        dispatchChange(extension, Event.REMOVE)
    }

    private fun dispatchChange(extension: T, event: Event) {
        myListeners.forEach {
            when (event) {
                Event.ADD -> it.extensionAdded(extension)
                Event.REMOVE -> it.extensionRemoved(extension)
            }
        }
    }

    fun <V> findExtension(clazz: Class<V>): V? {
        val targetExtension = myExtensions.find { clazz.isInstance(it) }
        if (targetExtension != null) {
            return targetExtension as V
        }
        return null
    }

    fun <V> findExtensions(clazz: Class<V>): List<T> {
        return myExtensions.filter { clazz.isInstance(it) }
    }


    private enum class Event {
        ADD, REMOVE
    }

}