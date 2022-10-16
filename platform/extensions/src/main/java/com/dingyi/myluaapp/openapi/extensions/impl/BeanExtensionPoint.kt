// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.impl.XmlExtensionAdapter.SimpleConstructorInjectionAdapter
import com.dingyi.myluaapp.openapi.service.ServiceRegistry

internal class BeanExtensionPoint<T : Any>(
    name: String,
    className: String,
    pluginDescriptor: PluginDescriptor,
    componentManager: ServiceRegistry,
    dynamic: Boolean,
) : ExtensionPointImpl<T>(name, className, pluginDescriptor, componentManager, null, dynamic),
    ImplementationClassResolver {

  override fun resolveImplementationClass(componentManager: ServiceRegistry, adapter: ExtensionComponentAdapter) = extensionClass

  override fun createAdapter(descriptor: ExtensionDescriptor,
                             pluginDescriptor: PluginDescriptor,
                             componentManager: ServiceRegistry): ExtensionComponentAdapter {
    return if (false /*componentManager.isInjectionForExtensionSupported*/) {
      SimpleConstructorInjectionAdapter(className, pluginDescriptor, descriptor, this)
    }
    else {
      XmlExtensionAdapter(className, pluginDescriptor, descriptor.orderId, descriptor.order,/* descriptor.element,*/ this)
    }
  }

  override fun unregisterExtensions(componentManager: ServiceRegistry,
                                    pluginDescriptor: PluginDescriptor,
                                    priorityListenerCallbacks: MutableList<in Runnable>,
                                    listenerCallbacks: MutableList<in Runnable>) {
    unregisterExtensions(false, priorityListenerCallbacks, listenerCallbacks) { it.pluginDescriptor !== pluginDescriptor }
  }
}
