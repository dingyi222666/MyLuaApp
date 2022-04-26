package com.dingyi.myluaapp.build.api.internal.provider

import com.dingyi.myluaapp.build.api.Transformer
import com.dingyi.myluaapp.build.api.provider.Provider

class TransformBackedProvider<OUT, IN>(
    private val transform: Transformer<out OUT, in IN>,
    private val provider: Provider<IN>
) : AbstractMinimalProvider<OUT>() {


    override fun calculateOwnValue(): Value<out OUT> {
        return Value.ofNullable(transform.transform(provider.get()))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TransformBackedProvider<*, *>) return false

        if (transform != other.transform) return false
        if (provider != other.provider) return false

        return true
    }
}
