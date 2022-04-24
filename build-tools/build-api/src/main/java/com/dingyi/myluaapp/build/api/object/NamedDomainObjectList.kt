package com.dingyi.myluaapp.build.api.`object`

import com.dingyi.myluaapp.build.api.sepcs.Spec


/**
 *
 * A specialization of [org.gradle.api.NamedDomainObjectCollection] that also implements [java.util.List].
 *
 *
 * All object equality is determined in terms of object names. That is, calling `remove()` with an object that is NOT equal to
 * an existing object in terms of `equals`, but IS in terms of name equality will result in the existing collection item with
 * the equal name being removed.
 *
 *
 * You can create an instance of this type using the factory method [org.gradle.api.model.ObjectFactory.namedDomainObjectList].
 *
 * @param <T> The type of objects in the list
</T> */
interface NamedDomainObjectList<T> : NamedDomainObjectCollection<T>,
    MutableList<T> {
    /**
     * {@inheritDoc}
     */
    override fun <S : T?> withType(type: Class<S>): NamedDomainObjectList<S>

    /**
     * {@inheritDoc}
     */
    override fun matching(spec: Spec<in T>): NamedDomainObjectList<T>


    /**
     * {@inheritDoc}
     */
    override fun findAll(spec: Spec<in T>): List<T>
}
