package com.dingyi.myluaapp.build.api.internal.provider

import com.google.common.collect.ImmutableList




/**
 * Carries either a value or some diagnostic information about where the value would have come from, had it been present.
 */
interface Value<T> {
    @Throws(IllegalStateException::class)
    fun get(): T
    fun orNull(): T?
    fun <S> orElse(defaultValue: S): S


    fun isMissing():Boolean

    fun <S> asType(): Value<S>

    companion object {
        fun <T> ofNullable(value: T?): Value<T> {
            return value?.let { Present(it) } ?: MISSING.asType()
        }

        fun <T> missing(): Value<T> {
            return MISSING.asType()
        }

        fun <T> of(value: T): Value<T> {
            return Present(value)
        }

        fun present(): Value<Unit> {
            return SUCCESS
        }

        val MISSING: Value<Any> = Missing()
        val SUCCESS: Value<Unit> = Present(Unit)
    }
}

class Present<T>(private val result: T) : Value<T> {

    override fun isMissing() = false

    @Throws(IllegalStateException::class)
    override fun get(): T {
        return result
    }

    override fun orNull(): T {
        return result
    }

    override fun <S> orElse(defaultValue: S): S {
        return result as S
    }


    override fun <S> asType(): Value<S> {
        throw IllegalStateException()
    }


}

class Missing<T>() : Value<T> {

    override fun isMissing() = true

    @Throws(IllegalStateException::class)
    override fun get(): T {
        throw IllegalStateException()
    }

    override fun orNull(): T? {
        return null
    }

    override fun <S> orElse(defaultValue: S): S {
        return defaultValue
    }


    override fun <S> asType(): Value<S> {
        return this as Value<S>
    }

}