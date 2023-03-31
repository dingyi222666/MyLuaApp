package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.util.Disposable
import java.util.function.Consumer


/**
 * Provides access to an [extension point](https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_extension_points.html). Instances of this class can be safely stored in static final fields.
 *
 * For project-level and module-level extension points use [ProjectExtensionPointName] instead to make it evident that corresponding
 * [AreaInstance] must be passed.
 */
class ExtensionPointName<T : Any>(name: String) : BaseExtensionPointName<T>(name) {

    val extensionList: List<T>
        get() = getPointImpl(null).extensionList

    val extensionsIfPointIsRegistered: List<T>
        get() = getExtensionsIfPointIsRegistered(null)

    fun getExtensionsIfPointIsRegistered(areaInstance: AreaInstance?): List<T> {
        val area = areaInstance?.extensionArea ?: Extensions.getRootArea()
        val point = area?.getExtensionPointIfRegistered<T>(name)
        return point?.extensionList ?: emptyList()
    }


    fun hasAnyExtensions(): Boolean {
        return getPointImpl(null).size != 0
    }

    /**
     * Consider using [ProjectExtensionPointName.getExtensions]
     */
    fun getExtensionList(areaInstance: AreaInstance?): List<T> {
        return getPointImpl(areaInstance).extensionList
    }

    /**
     * Consider using [ProjectExtensionPointName.getExtensions]
     */
    fun getExtensions(areaInstance: AreaInstance?): List<T> {
        return getPointImpl(areaInstance).extensionList
    }

    val point: ExtensionPoint<T>
        get() = getPointImpl(null)

    fun <V : T> findExtension(instanceOf: Class<V>): V {
        return getPointImpl(null).findExtension(instanceOf)  ?: throw IllegalArgumentException("No extension found for $instanceOf")
    }

    fun <V> findExtensions(instanceOf: Class<V>): List<T> {
        return getPointImpl(null).findExtensions(instanceOf)
    }

    fun <V : T> findExtensionOrFail(exactClass: Class<V>): V? {
        return getPointImpl(null).findExtension(exactClass)
    }


    fun addExtensionPointListener(
        listener: ExtensionPointListener<T>,
        parentDisposable: Disposable
    ) {
        getPointImpl(null).addExtensionPointListener(listener, parentDisposable)
    }

    fun addExtensionPointListener(listener: ExtensionPointListener<T>) {
        getPointImpl(null).addExtensionPointListener(listener, null)
    }

    fun addExtensionPointListener(
        areaInstance: AreaInstance,
        listener: ExtensionPointListener<T>
    ) {
        getPointImpl(areaInstance).addExtensionPointListener(listener, null)
    }

    fun removeExtensionPointListener(listener: ExtensionPointListener<T>) {
        getPointImpl(null).removeExtensionPointListener(listener)
    }

    fun addChangeListener(listener: Runnable, parentDisposable: Disposable?) {
        getPointImpl(null).addChangeListener(listener, parentDisposable)
    }


    companion object {
        fun <T : Any> create(name: String): ExtensionPointName<T> {
            return ExtensionPointName(name)
        }
    }
}