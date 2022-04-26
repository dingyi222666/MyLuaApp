package com.dingyi.myluaapp.build.api.internal.provider

import java.util.concurrent.Callable

class DefaultProvider<T>(
    private val callable: Callable<T>
): AbstractMinimalProvider<T>() {

    override fun calculateOwnValue(): Value<out T> {
        return Value.ofNullable(callable.call())
    }

    //equals method
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultProvider<*>) return false
        if (!super.equals(other)) return false
        if (callable != other.callable) return false
        return true
    }

}