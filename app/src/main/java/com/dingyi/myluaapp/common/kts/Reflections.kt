package com.dingyi.myluaapp.common.kts

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
fun <T> T?.checkNotNull(): T {
    return checkNotNull(this)
}

fun String.loadClass():Class<*> {
    return Class.forName(this)
}

inline fun <reified T> javaClass():Class<T> {
    return T::class.java
}

