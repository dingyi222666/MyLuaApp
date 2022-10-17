package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.util.BaseKeyedLazyInstance
import com.dingyi.myluaapp.util.KeyedLazyInstance


class FileTypeExtensionPoint<T:Any>(
    var filetype: String,
    private val __instance: T
) : BaseKeyedLazyInstance<T>(__instance), KeyedLazyInstance<T> {
    // these must be public for scrambling compatibility


    lateinit var implementationClass: String


    init {
        implementationClass = __instance::class.java.name
    }


    override val key: String
        get() = filetype
    override val implementationClassName: String?
        get() = implementationClass
}