// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions.impl


import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.impl.XmlExtensionAdapter.SimpleConstructorInjectionAdapter
import com.dingyi.myluaapp.openapi.service.ServiceRegistry

internal class InterfaceExtensionPoint<T : Any>(
    name: String,
    className: String,
    pluginDescriptor: PluginDescriptor,
    componentManager: ServiceRegistry,
    clazz: Class<T>?,
    dynamic: Boolean,
) : ExtensionPointImpl<T>(name, className, pluginDescriptor, componentManager, clazz, dynamic) {
    public override fun createAdapter(
        descriptor: ExtensionDescriptor,
        pluginDescriptor: PluginDescriptor,
        componentManager: ServiceRegistry
    ): ExtensionComponentAdapter {
        // see comment in readExtensions WHY element maybe created for interface extension point adapter
        // we cannot nullify element as part of readExtensions - in readExtensions not yet clear is it bean or interface extension
       /* if (!descriptor.hasExtraAttributes && descriptor.element != null && descriptor.element!!.children.isEmpty()) {
            descriptor.element = null
        }*/
        val implementationClassName = descriptor.implementation
            ?: throw /*componentManager.createError*/ error(
                "Attribute \"implementation\" is not specified for \"$name\" extension ${pluginDescriptor.pluginId}"
            )
        return SimpleConstructorInjectionAdapter(
            implementationClassName, pluginDescriptor, descriptor,
            InterfaceExtensionImplementationClassResolver.INSTANCE
        )
    }

    override fun unregisterExtensions(
        componentManager: ServiceRegistry,
        pluginDescriptor: PluginDescriptor,
        priorityListenerCallbacks: MutableList<in Runnable>,
        listenerCallbacks: MutableList<in Runnable>
    ) {
        unregisterExtensions(
            false,
            priorityListenerCallbacks,
            listenerCallbacks
        ) { it.pluginDescriptor !== pluginDescriptor }
    }
}
