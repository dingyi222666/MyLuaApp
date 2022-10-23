package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.util.KeyedLazyInstance


class ClassExtensionPoint<T> :
    KeyedLazyInstance<T>, BaseKeyedLazyInstance<T>() {
    // these must be public for scrambling compatibility

    lateinit var forClass: String


    lateinit var implementationClass: String


    override val implementationClassName: String?
        get() = implementationClass
    override val key: String
        get() = forClass


}