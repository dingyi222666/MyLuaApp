package com.dingyi.myluaapp.openapi.extensions


interface ExtensionPointListener<T> {
    companion object {
        private val EMPTY_ARRAY = arrayOfNulls<ExtensionPointListener<*>?>(0)

        fun <T> emptyArray(): Array<ExtensionPointListener<T>> {
            @Suppress("UNCHECKED_CAST")
            return EMPTY_ARRAY as Array<ExtensionPointListener<T>>
        }
    }


    fun extensionAdded(extension: T) {}

    fun extensionRemoved(extension: T) {}
}