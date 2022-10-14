// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.util.containers.CollectionFactory
import java.util.AbstractMap
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentMap
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier

// Separate class to keep ExtensionPointImpl class implementation clear and readable,
// such simple util code better to keep separately.
// getThreadSafeAdapterList can be opened to used here directly to avoid using of iterator, but it doesn't make sense
// - if there is already cached extension list, it will be used instead of custom iterator.
// It is not only about performance and common sense, but also supporting ability to mock extension list in tests (custom list is set).
object ExtensionProcessingHelper {
    fun <T> forEachExtensionSafe(iterable: Iterable<T>, extensionConsumer: Consumer<in T>) {
        for (t in iterable) {
            if (t == null) {
                break
            }
            try {
                extensionConsumer.accept(t)
            } catch (e: ProcessCanceledException) {
                throw e
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                ExtensionPointImpl.LOG.error(e)
            }
        }
    }

    fun <T> findFirstSafe(predicate: Predicate<in T?>, iterable: Iterable<T?>): T? {
        return computeSafeIfAny({ o: T? -> if (predicate.test(o)) o else null }, iterable)
    }

    fun <T, R> computeSafeIfAny(processor: Function<in T, out R?>, iterable: Iterable<T?>): R? {
        for (t in iterable) {
            if (t == null) {
                return null
            }
            try {
                val result = processor.apply(t)
                if (result != null) {
                    return result
                }
            } catch (e: ProcessCanceledException) {
                throw e
            } catch (e: Exception) {
                ExtensionPointImpl.LOG.error(e)
            }
        }
        return null
    }

    /**
     * See [com.intellij.openapi.extensions.ExtensionPointName.getByGroupingKey].
     */
    fun <K : Any?, T : Any?> getByGroupingKey(
        point: ExtensionPointImpl<T>,
        cacheId: Class<*>,
        key: K,
        keyMapper: Function<in T, out K>
    ): List<T> {
        val keyMapperToCache = point.getCacheMap<Class<*>, Map<K, MutableList<T>>>()
        var cache = keyMapperToCache[cacheId]
        if (cache == null) {
            cache = buildCacheForGroupingKeyMapper(keyMapper, point)
            val prev = keyMapperToCache.putIfAbsent(cacheId, cache)
            if (prev != null) {
                cache = prev
            }
        }
        val result: List<T>? = cache[key]
        return result ?: emptyList()
    }

    /**
     * See [com.intellij.openapi.extensions.ExtensionPointName.getByKey].
     */
    fun <K : Any, T : Any, V : Any> getByKey(
        point: ExtensionPointImpl<T>,
        key: K,
        cacheId: Class<*>,
        keyMapper: Function<in T, out K>,
        valueMapper: Function<in T, out V>
    ): V? {
        return doGetByKey(point, cacheId, key, keyMapper, valueMapper, point.getCacheMap())
    }

    /**
     * See [com.intellij.openapi.extensions.ExtensionPointName.getByKey].
     */
    fun <K : Any, T : Any> getByKey(
        point: ExtensionPointImpl<T>,
        key: K,
        cacheId: Class<*>,
        keyMapper: Function<in T, out K>
    ): T? {
        return doGetByKey(point, cacheId, key, keyMapper, Function.identity(), point.getCacheMap())
    }

    /**
     * See [com.intellij.openapi.extensions.ExtensionPointName.getByKey].
     */
    fun <K : Any, T : Any, V : Any> computeIfAbsent(
        point: ExtensionPointImpl<T>,
        key: K,
        cacheId: Class<*>,
        valueProducer: Function<in K, out V>
    ): V {
        // Or to have double look-up (map for valueProducer, map for key), or using of composite key. Java GC is quite good, so, composite key.
        val cache = point.getCacheMap<AbstractMap.SimpleImmutableEntry<K, Class<*>>, V>()
        return cache.computeIfAbsent(
            AbstractMap.SimpleImmutableEntry(
                key,
                cacheId
            )
        ) { (key1): AbstractMap.SimpleImmutableEntry<K, Class<*>> ->
            valueProducer.apply(
                key1
            )
        }
    }

    fun <T : Any, V : Any> computeIfAbsent(
        point: ExtensionPointImpl<T>,
        cacheId: Class<*>,
        valueProducer: Supplier<out V>
    ): V {
        val cache = point.getCacheMap<Class<*>, V>()
        return cache.computeIfAbsent(cacheId) { clazz: Class<*> -> valueProducer.get() }
    }

    private fun <CACHE_KEY, K, T, V> doGetByKey(
        point: ExtensionPoint<T>,
        cacheId: CACHE_KEY,
        key: K,
        keyMapper: Function<in T, out K>,
        valueMapper: Function<in T, out V>,
        keyMapperToCache: ConcurrentMap<CACHE_KEY, Map<K, V>>
    ): V? {
        var cache = keyMapperToCache[cacheId]
        if (cache == null) {
            cache = buildCacheForKeyMapper(keyMapper, valueMapper, point)
            val prev = keyMapperToCache.putIfAbsent(cacheId, cache)
            if (prev != null) {
                cache = prev
            }
        }
        return cache[key]
    }

    private fun <K, T> buildCacheForGroupingKeyMapper(
        keyMapper: Function<in T, out K>,
        point: ExtensionPoint<T>
    ): Map<K, MutableList<T>> {
        // use HashMap instead of THashMap - a lot of keys not expected, nowadays HashMap is a more optimized (e.g. computeIfAbsent implemented in an efficient manner)
        val cache: MutableMap<K, MutableList<T>> = HashMap()
        for (extension in point.extensionList) {
            val key: K = keyMapper.apply(extension)
            if (key != null) {
                // SmartList is not used - expected that list size will be > 1
                cache.computeIfAbsent(key) { k: K -> ArrayList() }.add(extension)
            }
        }
        return cache
    }

    private fun <K, T, V> buildCacheForKeyMapper(
        keyMapper: Function<in T, out K>,
        valueMapper: Function<in T, out V>,
        point: ExtensionPoint<T>
    ): Map<K, V> {
        val extensions = point.extensionList
        val cache = CollectionFactory.createSmallMemoryFootprintMap<K, V>(extensions.size)
        for (extension in extensions) {
            val key: K = keyMapper.apply(extension) ?: continue
            val value: V = valueMapper.apply(extension) ?: continue
            cache[key] = value
        }
        return cache
    }
}