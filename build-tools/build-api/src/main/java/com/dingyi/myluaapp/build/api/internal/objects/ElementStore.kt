package com.dingyi.myluaapp.build.api.internal.objects

import com.dingyi.myluaapp.build.api.provider.Provider

interface ElementStore<T>:Iterable<T> {

    override fun iterator(): Iterator<T>
    operator fun contains(element: Any): Boolean

    fun containsAll(elements: Collection<*>): Boolean

    fun isEmpty(): Boolean

    fun add(element: T): Boolean



    fun clear()

    fun addPending(provider: Provider<out T>): Boolean

    fun removePending(provider: Provider<out T>): Boolean

    fun addPendingCollection(provider:Provider<out Iterable<T>>): Boolean

    fun removePendingCollection(provider:Provider<out Iterable<T>>): Boolean


    fun remove(o: Any): Boolean

    fun size(): Int

}


class DefaultElementStore<T>: ElementStore<T> {

    private val allElementStore = mutableSetOf<Element<T>>()

    override fun iterator(): Iterator<T> {
        return createListIterator()
    }

    private fun createListIterator():ListIterator<T> {
        return allElementStore.flatMap { it.getValues() }.listIterator()
    }

    override fun contains(element: Any): Boolean {
      TODO("")
    }

    override fun containsAll(elements: Collection<*>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun addPending(provider: Provider<out T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePending(provider: Provider<out T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPendingCollection(provider: Provider<out Iterable<T>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePendingCollection(provider: Provider<out Iterable<T>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(o: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

}