package com.dingyi.myluaapp.build.api

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.RuntimeException
import kotlin.String


/**
 * A namer is capable of providing a name based on some inherent characteristic of an object.
 *
 * @param <T> The type of object that the namer can name
</T> */
interface Namer<T> {
    /**
     * Determines the name of the given object.
     *
     * @param object The object to determine the name of
     * @return The object's inherent name. Never null.
     * @throws RuntimeException If the name cannot be determined or is null
     */
    fun determineName(`object`: T): String

    /**
     * A comparator implementation based on the names returned by the given namer.
     *
     * @param <T> The type of object that the namer can name
    </T> */
    class Comparator<T>(private val namer: Namer<in T>) : java.util.Comparator<T> {
        override fun compare(o1: T, o2: T): Int {
            return namer.determineName(o1).compareTo(namer.determineName(o2))
        }

        override fun equals(other: Any?): Boolean {
            if (other== null || other !is Comparator<*>) {
                return false
            }
            return javaClass == other.javaClass && namer == other.namer
        }

        override fun hashCode(): Int {
            return 31 * namer.hashCode()
        }
    }
}
