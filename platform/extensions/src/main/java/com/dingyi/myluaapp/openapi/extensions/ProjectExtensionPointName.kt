// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionProcessingHelper
import com.intellij.openapi.Disposable
import com.intellij.util.ThreeState
import org.jetbrains.annotations.NonNls
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Stream

/**
 * Do not use.
 *
 *
 * Provides access to a project-level or module-level extension point. Since extensions are supposed to be stateless, storing different
 * instances of an extension for each project or module just waste the memory and complicates code, so **it's strongly recommended not
 * to introduce new project-level and module-level extension points**. If you need to have [Project][com.intellij.openapi.project.Project]
 * or [Module][com.intellij.openapi.module.Module] instance in some extension's method, just pass it as a parameter and use the default
 * application-level extension point.
 */
class ProjectExtensionPointName<T : Any>(@NonNls name: String) : BaseExtensionPointName<T>(name) {
    fun getPoint(areaInstance: AreaInstance): ExtensionPoint<T?> {
        return getPointImpl(areaInstance)
    }

    fun getExtensions(areaInstance: AreaInstance): List<T> {
        return getPointImpl(areaInstance).extensionList
    }

    @Deprecated("Use {@link #getExtensions(AreaInstance)}")
    fun extensions(areaInstance: AreaInstance): Stream<T> {
        return getPointImpl(areaInstance).extensionList.stream()
    }

    fun <V : T?> findExtension(instanceOf: Class<V>, areaInstance: AreaInstance): V? {
        return getPointImpl(areaInstance).findExtension(instanceOf, false, ThreeState.UNSURE)
    }

    fun <V : T> findExtensionOrFail(instanceOf: Class<V>, areaInstance: AreaInstance): V {
        return getPointImpl(areaInstance).findExtension(instanceOf, true, ThreeState.UNSURE)!!
    }

    fun hasAnyExtensions(areaInstance: AreaInstance): Boolean {
        return getPointImpl(areaInstance).size() != 0
    }

    fun findFirstSafe(areaInstance: AreaInstance, predicate: Predicate<in T?>): T? {
        return ExtensionProcessingHelper.findFirstSafe(predicate, getPointImpl(areaInstance))
    }

    fun <R> computeSafeIfAny(areaInstance: AreaInstance, processor: Function<in T, out R?>): R? {
        return ExtensionProcessingHelper.computeSafeIfAny(processor, getPointImpl(areaInstance))
    }

    fun addExtensionPointListener(
        areaInstance: AreaInstance,
        listener: ExtensionPointListener<T>,
        parentDisposable: Disposable
    ) {
        getPointImpl(areaInstance).addExtensionPointListener(listener, false, parentDisposable)
    }

    fun addChangeListener(
        areaInstance: AreaInstance,
        listener: Runnable,
        parentDisposable: Disposable
    ) {
        getPointImpl(areaInstance).addChangeListener(listener, parentDisposable)
    }

    fun processWithPluginDescriptor(
        areaInstance: AreaInstance,
        consumer: BiConsumer<in T, in PluginDescriptor>
    ) {
        getPointImpl(areaInstance).processWithPluginDescriptor(true, consumer)
    }

    fun getIterable(areaInstance: AreaInstance): Iterable<T> {
        return getPointImpl(areaInstance)
    }
}