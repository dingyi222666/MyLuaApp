package com.dingyi.myluaapp.build.api.internal.objects

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.objects.DomainObjectCollection
import com.dingyi.myluaapp.build.api.provider.Provider
import com.dingyi.myluaapp.build.api.sepcs.Spec

class DefaultDomainObjectCollection<T>:DomainObjectCollection<T> {


    override fun addLater(provider: Provider<out T>) {
        TODO("Not yet implemented")
    }

    override fun addAllLater(provider: Provider<out Iterable<T>>) {
        TODO("Not yet implemented")
    }

    override fun add(e: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(c: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : T> withType(type: Class<S>): DomainObjectCollection<S> {
        TODO("Not yet implemented")
    }

    override fun <S : T> withType(
        type: Class<S>,
        configureAction: Action<in S>
    ): DomainObjectCollection<S> {
        TODO("Not yet implemented")
    }

    override fun matching(spec: Spec<in T>): DomainObjectCollection<T> {
        TODO("Not yet implemented")
    }

    override fun whenObjectAdded(action: Action<in T>): Action<in T> {
        TODO("Not yet implemented")
    }

    override fun whenObjectRemoved(action: Action<in T>): Action<in T> {
        TODO("Not yet implemented")
    }

    override fun all(action: Action<in T>) {
        TODO("Not yet implemented")
    }

    override fun configureEach(action: Action<in T>) {
        TODO("Not yet implemented")
    }

    override fun findAll(spec: Spec<in T>): Collection<T> {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<T> {
        TODO("Not yet implemented")
    }

    override fun remove(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }
}