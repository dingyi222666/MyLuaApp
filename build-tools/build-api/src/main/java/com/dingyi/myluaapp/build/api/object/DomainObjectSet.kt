package com.dingyi.myluaapp.build.api.`object`

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.sepcs.Spec

interface DomainObjectSet<T>:DomainObjectCollection<T>,MutableSet<T> {

    override fun findAll(spec: Spec<in T>): DomainObjectSet<T>

    override fun matching(spec: Spec<in T>): DomainObjectSet<T>

    override fun <S : T> withType(type: Class<S>): DomainObjectSet<S>

    override fun <S : T> withType(
        type: Class<S>,
        configureAction: Action<in S>
    ): DomainObjectSet<S>


}