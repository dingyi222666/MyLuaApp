package com.dingyi.myluaapp.build.api.internal.objects

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.objects.DomainObjectCollection
import com.dingyi.myluaapp.build.api.provider.Provider
import com.dingyi.myluaapp.build.api.sepcs.Spec

class DefaultDomainObjectCollection<T>:DomainObjectCollection<T> {


    private val store = DefaultElementStore<T>()

    private val eventList = mutableListOf<Pair<String,Action<in T>>>()

    override fun addLater(provider: Provider<out T>) {
        store.addPending(provider)
    }

    override fun addAllLater(provider: Provider<out Iterable<T>>) {
        store.addPendingCollection(provider)
    }

    override fun add(e: T): Boolean {
        return store.add(e)
    }

    override fun addAll(c: Collection<T>): Boolean {
        return store.addAll(c)
    }

    override fun <S : T> withType(type: Class<S>): DomainObjectCollection<S> {
        return withType(type) {

        }
    }

    override fun <S : T> withType(
        type: Class<S>,
        configureAction: Action<in S>
    ): DomainObjectCollection<S> {
        return DefaultDomainObjectCollection<S>().also { collection ->
            store.forEach {
                if (type.isInstance(it)) {
                    val element = type.cast(it)
                    configureAction.execute(element)
                    collection.add(element)
                }
            }
        }
    }

    override fun matching(spec: Spec<in T>): DomainObjectCollection<T> {
        return DefaultDomainObjectCollection<T>().also { collection ->
            store.forEach {
                if (spec.isSatisfiedBy(it)) {
                    collection.add(it)
                }
            }
        }
    }

    override fun whenObjectAdded(action: Action<in T>): Action<in T> {
        eventList.add(Pair("add",action))
        return action
    }

    override fun whenObjectRemoved(action: Action<in T>): Action<in T> {
        eventList.add(Pair("remove",action))
        return action
    }

    override fun all(action: Action<in T>) {
        store.forEach {
            action.execute(it)
        }
    }

    override fun configureEach(action: Action<in T>) {
        store.forEach {
            action.execute(it)
        }
    }

    override fun findAll(spec: Spec<in T>): Collection<T> {
        return store.filter { spec.isSatisfiedBy(it) }
    }

    override val size: Int
        get() = store.size()

    override fun contains(element: T): Boolean {
        return store.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return store.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return store.isEmpty()
    }

    override fun clear() {
        store.clear()
    }

    override fun iterator(): MutableIterator<T> {
        return store.iterator()
    }

    override fun remove(element: T): Boolean {
        return store.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return store.removeAll(elements)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val deleteList = toList() - elements
        return store.removeAll(deleteList)
    }
}