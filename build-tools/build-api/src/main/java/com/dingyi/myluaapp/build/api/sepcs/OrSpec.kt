/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.build.api.sepcs


import com.google.common.collect.ObjectArrays

/**
 * A [CompositeSpec] which requires any one of its specs to be true in order to evaluate to
 * true. Uses lazy evaluation.
 *
 * @param <T> The target type for this Spec
</T> */
class OrSpec<T> : CompositeSpec<T> {
    constructor() : super() {}
    constructor(vararg specs: Spec<in T>) : super(*specs) {}
    constructor(specs: Iterable<Spec<in T>>) : super(specs) {}

    override fun isSatisfiedBy(`object`: T): Boolean {
        val specs = specsArray
        if (specs.isEmpty()) {
            return true
        }
        for (spec in specs) {
            if (spec.isSatisfiedBy(`object`)) {
                return true
            }
        }
        return false
    }

    fun or(vararg specs: Spec<in T>): OrSpec<T> {
        if (specs.isEmpty()) {
            return this
        }
        val thisSpecs = specsArray
        val thisLength = thisSpecs.size
        if (thisLength == 0) {
            return OrSpec(*specs)
        }
        val combinedSpecs: Array<Spec<in T>> = uncheckedCast(
            ObjectArrays.newArray(
                Spec::class.java, thisLength + specs.size
            )
        )
        System.arraycopy(thisSpecs, 0, combinedSpecs, 0, thisLength)
        System.arraycopy(specs, 0, combinedSpecs, thisLength, specs.size)
        return OrSpec(*combinedSpecs)
    }

    companion object {
        val EMPTY: OrSpec<*> = OrSpec<Any>()
        fun <T> empty(): OrSpec<T> {
            return uncheckedCast(EMPTY)
        }
    }
}