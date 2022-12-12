// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionProcessingHelper
import com.intellij.openapi.Disposable
import com.intellij.util.ThreeState
import org.jetbrains.annotations.NonNls
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier
import java.util.stream.Stream

/**
 * Provides access to an [extension point](https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_extension_points.html). Instances of this class can be safely stored in static final fields.
 *
 * For project-level and module-level extension points use [ProjectExtensionPointName] instead to make it evident that corresponding
 * [AreaInstance] must be passed.
 */
class ExtensionPointName<T : Any>(@NonNls name: String) : BaseExtensionPointName<T>(name) {
    /**
     * Consider using [.getExtensionList].
     */
    val extensions: Array<T>
        get() = getPointImpl(null).extensions
    val extensionList: List<T>
        get() = getPointImpl(null).extensionList

    /**
     * Invokes the given consumer for each extension registered in this extension point. Logs exceptions thrown by the consumer.
     */
    fun forEachExtensionSafe(consumer: Consumer<in T?>) {
        ExtensionProcessingHelper.forEachExtensionSafe(getPointImpl(null), consumer)
    }

    fun findFirstSafe(predicate: Predicate<in T?>): T? {
        return ExtensionProcessingHelper.findFirstSafe(predicate, getPointImpl(null))
    }

    fun <R> computeSafeIfAny(processor: Function<in T, out R?>): R? {
        return ExtensionProcessingHelper.computeSafeIfAny(processor, getPointImpl(null))
    }

    val extensionsIfPointIsRegistered: List<T>
        get() = getExtensionsIfPointIsRegistered(null)

    fun getExtensionsIfPointIsRegistered(areaInstance: AreaInstance?): List<T> {
        val area = areaInstance?.extensionArea ?: Extensions.getRootArea()
        val point = area?.getExtensionPointIfRegistered<T>(name)
        return point?.extensionList ?: emptyList()
    }

    @Deprecated("Use {@code getExtensionList().stream()}")
    fun extensions(): Stream<T> {
        return getPointImpl(null).extensions()
    }

    fun hasAnyExtensions(): Boolean {
        return getPointImpl(null).size() != 0
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
    fun getExtensions(areaInstance: AreaInstance?): Array<T> {
        return getPointImpl(areaInstance).extensions
    }

    @Deprecated("Use app-level app extension point.")
    fun extensions(areaInstance: AreaInstance?): Stream<T> {
        return getPointImpl(areaInstance).extensionList.stream()
    }

    @Deprecated(
        """use {@link #getPoint()} to access application-level extensions and {@link ProjectExtensionPointName#getPoint(AreaInstance)}
      to access project-level and module-level extensions"""
    )
    fun getPoint(areaInstance: AreaInstance?): ExtensionPoint<T?> {
        return getPointImpl(areaInstance)
    }

    val point: ExtensionPoint<T>
        get() = getPointImpl(null)

    fun <V : T> findExtension(instanceOf: Class<V>): V? {
        return getPointImpl(null).findExtension(instanceOf, false, ThreeState.UNSURE)
    }

    fun <V> findExtensions(instanceOf: Class<V>): List<T> {
        return getPointImpl(null).findExtensions(instanceOf)
    }

    fun <V : T> findExtensionOrFail(exactClass: Class<V>): V? {
        return getPointImpl(null).findExtension(exactClass, true, ThreeState.UNSURE)
    }

    fun <V : T> findFirstAssignableExtension(instanceOf: Class<V>): V? {
        return getPointImpl(null).findExtension(instanceOf, true, ThreeState.NO)
    }

    /**
     * Do not use it if there is any extension point listener, because in this case behaviour is not predictable -
     * events will be fired during iteration and probably it will be not expected.
     *
     *
     * Use only for interface extension points, not for bean.
     *
     *
     * Due to internal reasons, there is no easy way to implement hasNext in a reliable manner,
     * so, `next` may return `null` (in this case stop iteration).
     *
     *
     * Possible use cases:
     * 1. Conditional iteration (no need to create all extensions if iteration will be stopped due to some condition).
     * 2. Iterated only once per application (no need to cache extension list internally).
     */
    val iterable: Iterable<T?>
        get() = getPointImpl(null)

    fun processWithPluginDescriptor(consumer: BiConsumer<in T, in PluginDescriptor>) {
        getPointImpl(null).processWithPluginDescriptor( /* shouldBeSorted = */true, consumer)
    }

    fun addExtensionPointListener(
        listener: ExtensionPointListener<T>,
        parentDisposable: Disposable
    ) {
        getPointImpl(null).addExtensionPointListener(listener, false, parentDisposable)
    }

    fun addExtensionPointListener(listener: ExtensionPointListener<T>) {
        getPointImpl(null).addExtensionPointListener(listener, false, null)
    }

    fun addExtensionPointListener(
        areaInstance: AreaInstance,
        listener: ExtensionPointListener<T>
    ) {
        getPointImpl(areaInstance).addExtensionPointListener(listener, false, null)
    }

    fun removeExtensionPointListener(listener: ExtensionPointListener<T>) {
        getPointImpl(null).removeExtensionPointListener(listener)
    }

    fun addChangeListener(listener: Runnable, parentDisposable: Disposable?) {
        getPointImpl(null).addChangeListener(listener, parentDisposable)
    }

    /**
     * Build cache by arbitrary key using provided key to value mapper. Values with the same key merge into list. Return values by key.
     *
     *
     * To exclude extension from cache, return null key.
     *
     *
     * `cacheId` is required because it's dangerous to rely on identity of functional expressions.
     * JLS doesn't specify whether a new instance is produced or some common instance is reused for lambda expressions (see 15.27.4).
     */
    fun <K : Any> getByGroupingKey(
        key: K,
        cacheId: Class<*>,
        keyMapper: Function<in T, out K>
    ): List<T> {
        return ExtensionProcessingHelper.getByGroupingKey(
            getPointImpl(null),
            cacheId,
            key,
            keyMapper
        )
    }

    /**
     * Build cache by arbitrary key using provided key to value mapper. Return value by key.
     *
     *
     * To exclude extension from cache, return null key.
     */
    fun <K : Any> getByKey(key: K, cacheId: Class<*>, keyMapper: Function<in T, out K>): T? {
        return ExtensionProcessingHelper.getByKey(getPointImpl(null), key, cacheId, keyMapper)
    }

    /**
     * Build cache by arbitrary key using provided key to value mapper. Return value by key.
     *
     *
     * To exclude extension from cache, return null key.
     */
    fun <K : Any, V : Any> getByKey(
        key: K,
        cacheId: Class<*>,
        keyMapper: Function<in T, out K>,
        valueMapper: Function<in T, out V>
    ): V? {
        return ExtensionProcessingHelper.getByKey(
            getPointImpl(null),
            key,
            cacheId,
            keyMapper,
            valueMapper
        )
    }

    fun <K : Any, V : Any> computeIfAbsent(
        key: K,
        cacheId: Class<*>,
        valueMapper: Function<in K, out V>
    ): V {
        return ExtensionProcessingHelper.computeIfAbsent(
            getPointImpl(null),
            key,
            cacheId,
            valueMapper
        )
    }

    /**
     * Cache some value per extension point.
     */
    fun <V : Any?> computeIfAbsent(cacheId: Class<*>, valueMapper: Supplier<out V>): V {
        return ExtensionProcessingHelper.computeIfAbsent(getPointImpl(null), cacheId, valueMapper)
    }

    companion object {
        fun <T : Any> create(@NonNls name: String): ExtensionPointName<T> {
            return ExtensionPointName(name)
        }
    }
}