package com.dingyi.myluaapp.build.api.internal.objects

import com.dingyi.myluaapp.build.api.provider.Provider

interface ElementStore<T>:Iterable<T> {

    override fun iterator(): Iterator<T>
    operator fun contains(element: T): Boolean

    fun containsAll(elements: Collection<T>): Boolean

    fun isEmpty(): Boolean

    fun add(element: T): Boolean

    fun addAll(elements: Collection<T>): Boolean

    fun removeAll(elements: Collection<T>): Boolean

    fun clear()

    fun addPending(provider: Provider<out T>): Boolean

    fun removePending(provider: Provider<out T>): Boolean

    fun addPendingCollection(provider:Provider<out Iterable<T>>): Boolean

    fun removePendingCollection(provider:Provider<out Iterable<T>>): Boolean


    fun remove(o: T): Boolean

    fun size(): Int

}


class DefaultElementStore<T>: ElementStore<T> {

    private val allElementStore = mutableSetOf<Element<T>>()

    override fun iterator(): MutableIterator<T> {
        return createListIterator()
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return elements.map { add(it) }.all { true }
    }

    private fun createListIterator(): MutableListIterator<T> {
        return allElementStore.flatMap { it.getValues() }.toMutableList().listIterator()
    }

    override fun contains(element: T): Boolean {
        return allElementStore.any { it.contains(element) }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return createListIterator().asSequence().toList().containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return allElementStore.isEmpty()
    }

    override fun add(element: T): Boolean {
        return allElementStore.add(SingleElement(element))
    }

    override fun clear() {
        allElementStore.clear()
    }

    override fun addPending(provider: Provider<out T>): Boolean {
        return allElementStore.add(SingleProviderElement(provider))
    }

    override fun removePending(provider: Provider<out T>): Boolean {
        return allElementStore.remove(SingleProviderElement(provider))
    }

    override fun addPendingCollection(provider: Provider<out Iterable<T>>): Boolean {
        return allElementStore.add(IterableProvider(provider))
    }

    override fun removePendingCollection(provider: Provider<out Iterable<T>>): Boolean {
        return allElementStore.remove(IterableProvider(provider))
    }

    override fun remove(o: T): Boolean {
        return allElementStore.removeIf { it.remove(o) }
    }

    override fun size(): Int {
        return createListIterator().asSequence().toList().size
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return allElementStore.filter { it.removeAll(elements) }.all { true }
    }

}