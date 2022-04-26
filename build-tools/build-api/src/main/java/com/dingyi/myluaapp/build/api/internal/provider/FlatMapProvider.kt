package com.dingyi.myluaapp.build.api.internal.provider

import com.dingyi.myluaapp.build.api.Transformer
import com.dingyi.myluaapp.build.api.provider.Provider

class FlatMapProvider<S, T>(
    private val transformer: Transformer<out Provider<out S>, in T>,
    private val source: Provider<T>
) : AbstractMinimalProvider<S>() {

    override fun calculateOwnValue(): Value<out S> {
        return Value.ofNullable(transformer.transform(source.getOrNull()).getOrNull())
    }

    //equals method
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FlatMapProvider<*, *>) return false

        if (transformer != other.transformer) return false
        if (source != other.source) return false

        return true
    }
}