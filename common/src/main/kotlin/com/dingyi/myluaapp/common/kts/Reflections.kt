package com.dingyi.myluaapp.common.kts

import org.luaj.vm2.LuajVm
import kotlin.Pair

/**
 * @author: dingyi
 * @date: 2021/8/4 16:31
 * @description:
 **/


inline fun <reified T> Any.getPrivateField(name: String): T {
    val field = this.javaClass.getDeclaredField(name)
    field.isAccessible = true
    return field.get(this) as T
}

inline fun <reified T> Class<T>.getPrivateField(obj: Any?, name: String): T {
    val field = getDeclaredField(name)
    field.isAccessible = true
    return field.get(obj) as T
}

/**
 * Throws an [IllegalStateException] if the [value] is null. Otherwise
 * returns the not null value.
 *
 */
inline fun <T> T?.checkNotNull(): T {
    return checkNotNull(this)
}

inline fun String.loadClass(): Class<*> {
    return Class.forName(this)
}

inline fun <reified T> Any.convertObject():T {
    return this as T
}

inline fun <reified T> getJavaClass(): Class<T> {
    return T::class.java
}

inline fun println(vararg  args:Any) {
    val buffer = StringBuilder()
    args.forEach {
        buffer.append(it.toString()).append(" ")
    }
    kotlin.io.println(buffer)
}

data class MutablePair<A,B>(
   var first: A,
   var second: B
) : java.io.Serializable {

    /**
     * Returns string representation of the [Pair] including its [first] and [second] values.
     */
   override fun toString(): String = "($first, $second)"
}

typealias LuaJVM = LuajVm


