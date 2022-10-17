package com.dingyi.myluaapp.common.ktx

import org.luaj.vm2.LuaJVM
import kotlin.Pair

/**
 * @author: dingyi
 * @date: 2021/8/4 16:31
 * @description:
 **/


inline fun <reified T> Any.getPrivateField(name: String): T {
    return this.javaClass.getDeclaredField(name).apply {
        isAccessible = true
    }.get(this) as T
}

inline fun <reified T> Class<T>.getPrivateField(obj: Any?, name: String): T {
    return getDeclaredField(name).apply {
        isAccessible = true
    }.get(obj) as T
}

inline fun <reified T> Any?.setPrivateField(name: String, obj: Any?) {
    return getJavaClass<T>().getDeclaredField(name).apply {
        isAccessible = true
    }.set(this, obj)
}


/**
 * Throws an [IllegalStateException] if self is null. Otherwise
 * returns the not null value.
 *
 */
inline fun <T> T?.checkNotNull(): T {
    return this!!
}

inline fun <T> T?.ifNull(block: () -> Unit) {
    if (this == null) {
        block()
    }
}

inline fun String.loadClass(): Class<*> {
    return Class.forName(this)
}

inline fun <reified T> Any.convertObject(): T {
    return this as T
}

inline fun <reified T> getJavaClass(): Class<T> {
    return T::class.java
}

inline fun println(vararg args: Any?) {
    val buffer = StringBuilder()
    for (arg in args) {
        buffer.append(arg.toString()).append(" ")
    }
    kotlin.io.println(buffer)
}

data class MutablePair<A, B>(
    var first: A,
    var second: B
) : java.io.Serializable {

    constructor(pair: Pair<A, B>) : this(pair.first, pair.second)


    /**
     * Returns string representation of the [Pair] including its [first] and [second] values.
     */
    override fun toString(): String = "($first, $second)"

}

fun <A, B> Pair<A, B>.toMutablePair(): MutablePair<A, B> = MutablePair(this)

typealias LuaJVM = LuaJVM


