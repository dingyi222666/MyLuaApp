package com.dingyi.myluaapp.build.internal.metaobject

class DynamicInvokeResult private constructor(val value: Any?) {

    companion object {
        @JvmStatic
        private val NO_VALUE = Any()

        @JvmStatic
        private val NOT_FOUND = DynamicInvokeResult(NO_VALUE)

        @JvmStatic
        private val NULL = DynamicInvokeResult(null)

        fun found(value: Any?): DynamicInvokeResult {
            return if (value == null) found() else DynamicInvokeResult(
                value
            )
        }

        fun found(): DynamicInvokeResult {
            return DynamicInvokeResult.NULL
        }

        fun notFound(): DynamicInvokeResult {
            return DynamicInvokeResult.NOT_FOUND
        }

    }
}