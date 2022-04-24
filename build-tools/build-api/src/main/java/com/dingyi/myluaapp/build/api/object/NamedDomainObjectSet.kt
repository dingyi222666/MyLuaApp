package com.dingyi.myluaapp.build.api.`object`

import com.dingyi.myluaapp.build.api.sepcs.Spec


/**
 *
 * A specialization of [NamedDomainObjectCollection] that also implements [Set] and orders objects by their inherent name.
 *
 *
 * All object equality is determined in terms of object names. That is, calling `remove()` with an object that is NOT equal to
 * an existing object in terms of `equals`, but IS in terms of name equality will result in the existing collection item with
 * the equal name being removed.
 *
 *
 *
 * @param <T> The type of objects in the set
</T> */
interface NamedDomainObjectSet<T> : NamedDomainObjectCollection<T>,
    MutableSet<T> {
    /**
     * {@inheritDoc}
     */
    override fun <S : T?> withType(type: Class<S>): NamedDomainObjectSet<S>

    /**
     * {@inheritDoc}
     */
    override fun matching(spec: Spec<in T>): NamedDomainObjectSet<T>


    /**
     * {@inheritDoc}
     */
    override fun findAll(spec: Spec<in T>): Set<T>
}
