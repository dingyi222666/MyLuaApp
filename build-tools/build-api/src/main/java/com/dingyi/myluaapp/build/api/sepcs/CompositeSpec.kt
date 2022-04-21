/*
 * Copyright 2010 the original author or authors.
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

import com.google.common.collect.Iterators
import java.util.*

/**
 * A [Spec] which aggregates a sequence of other `Spec` instances.
 *
 * @param <T> The target type for this Spec
</T> */
abstract class CompositeSpec<T> : Spec<T> {
    // Not public. Evaluation of these specs is a major hot spot for large builds, so use an array for iteration
    val specsArray: Array<Spec<in T>>

    protected constructor() {
        specsArray = uncheckedCast(EMPTY)
    }

    protected constructor(vararg specs: Spec<in T>) {
        if (specs.size == 0) {
            specsArray = uncheckedCast(EMPTY)
        } else {
            specsArray = specs.clone() as Array<Spec<in T>>
        }
    }

    protected constructor(specs: Iterable<Spec<in T>>) {
        if (specs is Collection<*>) {
            val specCollection: Collection<Spec<in T>> = uncheckedCast(specs)
            if (specCollection.isEmpty()) {
                specsArray = uncheckedCast(EMPTY)
            } else {
                specsArray = uncheckedCast(specCollection.toTypedArray())
            }
        } else {
            val iterator = specs.iterator()
            if (!iterator.hasNext()) {
                specsArray = uncheckedCast(EMPTY)
            } else {
                specsArray = uncheckedCast(Iterators.toArray(iterator, Spec::class.java))
            }
        }
    }

    fun getSpecs(): List<Spec<in T>> {
        return Collections.unmodifiableList(Arrays.asList(*specsArray))
    }

    val isEmpty: Boolean
        get() = specsArray.size == 0

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as CompositeSpec<*>
        return Arrays.equals(specsArray, that.specsArray)
    }

    override fun hashCode(): Int {
        var result = javaClass.hashCode()
        result = 31 * result + Arrays.hashCode(specsArray)
        return result
    }


    companion object {
        @JvmStatic
        private val EMPTY: Array<Spec<*>> = arrayOf()
        @JvmStatic
        fun <T> uncheckedCast(`object`: Any): T {
            return `object` as T
        }
    }
}