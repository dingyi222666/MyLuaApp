package com.dingyi.myluaapp.build.api.internal.provider

class ValueProvider<T>(
    private val value: T?
): AbstractMinimalProvider<T>() {

    override fun calculateOwnValue() = Value.ofNullable(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValueProvider<*>) return false

        if (value != other.value) return false

        return true
    }

    companion object {
        fun <T> of(value: T?): ValueProvider<T> = ValueProvider(value)
    }
}