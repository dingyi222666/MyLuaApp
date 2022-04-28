package com.dingyi.myluaapp.build.api.internal.objects

import com.dingyi.myluaapp.build.api.provider.Provider

interface Element<T> {

    fun size(): Int

    fun getValues(): List<T>

    fun clear()

    fun remove(index: Int): T

    fun remove(element: T): Boolean

    fun removeAll(elements: Collection<T>): Boolean

    fun contains(element: T): Boolean

}

class SingleElement<T>(
    value: T
) : Element<T> {

    private val list = mutableListOf(value)

    override fun size(): Int {
        return list.size
    }

    override fun getValues(): List<T> {
        return list
    }

    override fun clear() {
        list.clear()
    }

    override fun remove(index: Int): T {
        return list.removeAt(index)
    }

    override fun remove(element: T): Boolean {
        return list.remove(element)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleElement<*>

        if (list != other.list) return false

        return true
    }

    override fun contains(element: T): Boolean {
        return list.contains(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return list.removeAll(elements)
    }

}


class IterableProvider<T>(
    private val provider: Provider<out Iterable<T>>
) : Element<T> {

    private val list = mutableListOf<T>()

    private var isInit = false

    private fun realize() {
        if (!isInit) {
            provider.get().forEach {
                list.add(it)
            }
            isInit = true
        }
    }

    override fun size(): Int {
        realize()
        return list.size
    }

    override fun getValues(): List<T> {
        realize()
        return list
    }

    override fun contains(element: T): Boolean {
        realize()
        return list.contains(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        realize()
        return list.removeAll(elements)
    }

    override fun clear() {
        list.clear()
    }

    override fun remove(index: Int): T {
        realize()
        return list.removeAt(index)
    }

    override fun remove(element: T): Boolean {
        realize()
        return list.remove(element)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IterableProvider<*>

        if (this.provider != other.provider) return false

        return true
    }


}

class SingleProviderElement<T>(
    private val provider: Provider<out T>
):  Element<T> {

    private val list = mutableListOf<T>()

    private var isInit = false

    private fun realize() {
        if (!isInit) {
            list.clear()
            list.add(provider.get())
            isInit = true
        }
    }

    override fun contains(element: T): Boolean {
        realize()
        return list.contains(element)
    }

    override fun size(): Int {
        realize()
        return list.size
    }

    override fun getValues(): List<T> {
        realize()
        return list
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        realize()
        return list.removeAll(elements)
    }

    override fun clear() = list.clear()

    override fun remove(index: Int): T {
        realize()
        return list.removeAt(index)
    }

    override fun remove(element: T): Boolean {
        realize()
        return list.remove(element)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleProviderElement<*>

        if (this.provider != other.provider) return false

        return true
    }
}