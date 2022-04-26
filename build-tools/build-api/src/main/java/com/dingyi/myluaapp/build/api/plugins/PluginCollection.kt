package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Plugin
import com.dingyi.myluaapp.build.api.objects.DomainObjectSet
import com.dingyi.myluaapp.build.api.sepcs.Spec


/**
 *
 * A `PluginCollection` represents a collection of [org.gradle.api.Plugin] instances.
 *
 * @param <T> The type of plugins which this collection contains.
</T> */
interface PluginCollection<T : Plugin<*>> : DomainObjectSet<T> {
    /**
     * {@inheritDoc}
     */
    override fun matching(spec: Spec<in T>): PluginCollection<T>



    /**
     * {@inheritDoc}
     */
    override fun <S : T> withType(type: Class<S>): PluginCollection<S>

    /**
     * Adds an `Action` to be executed when a plugin is added to this collection.
     *
     * @param action The action to be executed
     * @return the supplied action
     */
    fun whenPluginAdded(action: Action<in T>): Action<in T>



    /**
     * Unsupported.
     */
    @Deprecated("Use {@link PluginManager#apply(Class)} instead.")
    override fun add(plugin: T): Boolean

    /**
     * Unsupported.
     */
    @Deprecated("Use {@link PluginManager#apply(Class)} instead.")
    override fun addAll(c: Collection<T>): Boolean

    /**
     * Unsupported.
     */
    @Deprecated("plugins cannot be removed.")
    override fun remove(o: T): Boolean

    /**
     * Unsupported.
     */
    @Deprecated("plugins cannot be removed.")
    override fun removeAll(c: Collection<T>): Boolean

    /**
     * Unsupported.
     */
    @Deprecated("plugins cannot be removed.")
    override fun clear()
}
