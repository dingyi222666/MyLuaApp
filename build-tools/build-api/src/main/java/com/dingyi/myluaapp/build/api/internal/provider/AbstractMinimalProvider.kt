package com.dingyi.myluaapp.build.api.internal.provider

import com.dingyi.myluaapp.build.api.Transformer
import com.dingyi.myluaapp.build.api.provider.Provider

abstract class AbstractMinimalProvider<T> : Provider<T> {



    override fun <S> map(transformer: Transformer<out S, in T>): Provider<S> {
        return TransformBackedProvider(transformer, this)
    }

    override fun <S> flatMap(transformer: Transformer<out Provider<out S>, in T>): Provider<S> {
        return FlatMapProvider(transformer, this)
    }

    protected abstract fun calculateOwnValue(): Value<out T>

    override fun get(): T {
        return calculateOwnValue().get()
    }

    override fun getOrElse(defaultValue: T): T {
        return calculateOwnValue().orNull() ?: defaultValue
    }

    override fun getOrNull(): T? {
        return calculateOwnValue().orNull()
    }

    override fun orElse(value: T): Provider<T> {
        return DefaultProvider { getOrElse(value) }
    }

    override fun orElse(provider: Provider<out T>): Provider<T> {
        return DefaultProvider { getOrElse(provider.get()) }
    }


}