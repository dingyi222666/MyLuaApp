// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.serviceContainer


import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.openapi.components.ComponentManager
import com.dingyi.myluaapp.openapi.extensions.PluginId
import org.picocontainer.ComponentAdapter
import java.lang.reflect.Constructor

@Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
private val badAppLevelClasses = setOf(
  "com.intellij.execution.executors.DefaultDebugExecutor",
  "org.apache.http.client.HttpClient",
  "org.apache.http.impl.client.CloseableHttpClient",
  "com.intellij.openapi.project.Project"
)

internal class ConstructorParameterResolver {
  fun isResolvable(componentManager: ComponentManagerImpl,
                   requestorKey: Any,
                   requestorClass: Class<*>,
                   requestorConstructor: Constructor<*>,
                   expectedType: Class<*>,
                   pluginId: PluginId,
                   isExtensionSupported: Boolean): Boolean {
    if (expectedType === ComponentManager::class.java ||
        findTargetAdapter(componentManager, expectedType, requestorKey, requestorClass, requestorConstructor, pluginId) != null) {
      return true
    }
    return isExtensionSupported && componentManager.getExtensionArea().findExtensionByClass(expectedType) != null
  }

  fun resolveInstance(componentManager: ComponentManagerImpl,
                      requestorKey: Any,
                      requestorClass: Class<*>,
                      requestorConstructor: Constructor<*>,
                      expectedType: Class<*>,
                      pluginId: PluginId): Any? {
    if (expectedType === ComponentManager::class.java) {
      return componentManager
    }


    val adapter = findTargetAdapter(componentManager, expectedType, requestorKey, requestorClass, requestorConstructor, pluginId)
                  ?: return handleUnsatisfiedDependency(componentManager, requestorClass, expectedType, pluginId)
    return when {
      adapter is BaseComponentAdapter -> {
        // project level service Foo wants application level service Bar - adapter component manager should be used instead of current
        adapter.getInstance(adapter.componentManager, null)
      }
      componentManager.parent == null -> adapter.componentInstance
      else -> componentManager.getComponentInstance(adapter.componentKey)
    }
  }

  private fun handleUnsatisfiedDependency(componentManager: ComponentManagerImpl, requestorClass: Class<*>, expectedType: Class<*>, pluginId: PluginId): Any? {
    val extension = componentManager.getExtensionArea().findExtensionByClass(expectedType) ?: return null
    val message = "Do not use constructor injection to get extension instance (requestorClass=${requestorClass.name}, extensionClass=${expectedType.name})"
    val app = componentManager.getApplication()
    @Suppress("SpellCheckingInspection")
    if (app != null && app.isUnitTestMode && pluginId.idString != "org.jetbrains.kotlin" && pluginId.idString != "Lombook Plugin") {
      throw PluginException(message, pluginId)
    }
    else {
      LOG.warn(message)
    }
    return extension
  }
}

private fun findTargetAdapter(componentManager: ComponentManagerImpl,
                              expectedType: Class<*>,
                              requestorKey: Any,
                              requestorClass: Class<*>,
                              requestorConstructor: Constructor<*>,
                              pluginId: PluginId): ComponentAdapter? {
  val byKey = componentManager.getComponentAdapter(expectedType)
  if (byKey != null && requestorKey != byKey.componentKey) {
    return byKey
  }

  val className = expectedType.name
  if (componentManager.parent == null) {
    if (badAppLevelClasses.contains(className)) {
      return null
    }
  }
  else if (className == "com.intellij.configurationStore.StreamProvider" ||
           className == "com.intellij.openapi.roots.LanguageLevelModuleExtensionImpl" ||
           className == "com.intellij.openapi.roots.impl.CompilerModuleExtensionImpl" ||
           className == "com.intellij.openapi.roots.impl.JavaModuleExternalPathsImpl") {
    return null
  }

  if (componentManager.isGetComponentAdapterOfTypeCheckEnabled) {
    LOG.error(PluginException("getComponentAdapterOfType is used to get ${expectedType.name} (requestorClass=${requestorClass.name}, requestorConstructor=${requestorConstructor})." +
                              "\n\nProbably constructor should be marked as NonInjectable.", pluginId))
  }
  return componentManager.getComponentAdapterOfType(expectedType) ?: componentManager.parent?.getComponentAdapterOfType(expectedType)
}