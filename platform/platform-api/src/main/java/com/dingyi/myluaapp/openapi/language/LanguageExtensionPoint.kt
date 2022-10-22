package com.dingyi.myluaapp.openapi.language

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.util.CustomLoadingExtensionPointBean
import com.dingyi.myluaapp.util.KeyedLazyInstance


/**
 * Base class for [Language]-bound extension points.
 */
open class LanguageExtensionPoint<T:Any> : CustomLoadingExtensionPointBean<T>, KeyedLazyInstance<T> {
    // these must be public for scrambling compatibility
    /**
     * Language ID.
     *
     * @see Language.getID
     */

    override var key: String


    override var implementationClassName: String


    constructor(
         language: String,
         implementationClass: String,
         pluginDescriptor: PluginDescriptor
    ) {
        key = language
        implementationClassName = implementationClass
        setPluginDescriptor(pluginDescriptor)
    }


    constructor(language: String,  instance: T) : super(instance) {
        key = language
        implementationClassName = instance::class.java.name
    }
}
