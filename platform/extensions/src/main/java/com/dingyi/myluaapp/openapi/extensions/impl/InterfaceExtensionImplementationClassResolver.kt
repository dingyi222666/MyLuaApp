// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.InternalIgnoreDependencyViolation
import com.dingyi.myluaapp.openapi.service.ServiceRegistry


internal fun interface ImplementationClassResolver {
    fun resolveImplementationClass(
        componentManager: ServiceRegistry, adapter: ExtensionComponentAdapter
    ): Class<*>
}

internal class InterfaceExtensionImplementationClassResolver private constructor() :
    ImplementationClassResolver {
    companion object {
        @JvmField
        val INSTANCE = InterfaceExtensionImplementationClassResolver()
    }

    override fun resolveImplementationClass(
        componentManager: ServiceRegistry, adapter: ExtensionComponentAdapter
    ): Class<*> {
        val className = adapter.implementationClassOrName
        if (className !is String) {
            return className as Class<*>
        }

        val pluginDescriptor = adapter.pluginDescriptor
        val result =
            pluginDescriptor.classLoader.loadClass(className) //componentManager.loadClass<Any>(className, pluginDescriptor)
        @Suppress("SpellCheckingInspection") if (result.classLoader !== pluginDescriptor.pluginClassLoader && pluginDescriptor.pluginClassLoader != null && !className.startsWith(
                "com.dingyi.myluaapp.webcore.resourceRoots."
            ) && !className.startsWith("com.dingyi.myluaapp.tasks.impl.") && !result.isAnnotationPresent(
                InternalIgnoreDependencyViolation::class.java
            )
        ) {
            val idString = pluginDescriptor.pluginId.idString
            if (idString != "com.dingyi.myluaapp.java" && idString != "com.dingyi.myluaapp.java.ide" && idString != "org.jetbrains.android" && idString != "com.dingyi.myluaapp.kotlinNative.platformDeps" && idString != "com.jetbrains.rider.android") {
                ExtensionPointImpl.LOG.error(
                    """Created extension classloader is not equal to plugin's one.
See https://youtrack.jetbrains.com/articles/IDEA-A-65/Plugin-Model#internalignoredependencyviolation
(
  className=$className,
  extensionInstanceClassloader=${result.classLoader},
  pluginClassloader=${pluginDescriptor.pluginClassLoader}
) ${pluginDescriptor.pluginId}"""
                )
            }
        }
        adapter.implementationClassOrName = result
        return result
    }
}