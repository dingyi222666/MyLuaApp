// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
@file:ApiStatus.Internal
package com.dingyi.myluaapp.serviceContainer

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.progress.ProgressIndicatorProvider
import com.dingyi.myluaapp.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Cancellation
import com.intellij.openapi.progress.ProcessCanceledException
import org.jetbrains.annotations.ApiStatus

internal fun checkCanceledIfNotInClassInit() {
  try {
    ProgressManager.checkCanceled()
  }
  catch (e: ProcessCanceledException) {
    // otherwise ExceptionInInitializerError happens and the class is screwed forever
    @Suppress("SpellCheckingInspection")
    if (!e.stackTrace.any { it.methodName == "<clinit>" }) {
      throw e
    }
  }
}

internal fun isGettingServiceAllowedDuringPluginUnloading(descriptor: PluginDescriptor): Boolean {
  return descriptor.isRequireRestart /*||
         descriptor.pluginId == PluginManagerCore.CORE_ID || descriptor.pluginId == PluginManagerCore.JAVA_PLUGIN_ID*/
}

@ApiStatus.Internal
fun isUnderIndicatorOrJob(): Boolean {
  return ProgressIndicatorProvider.globalProgressIndicator != null || Cancellation.currentJob() != null
}

@ApiStatus.Internal
fun throwAlreadyDisposedError(serviceDescription: String, componentManager: ComponentManagerImpl) {
  val error = RuntimeException("Cannot create $serviceDescription because container is already disposed (container=${componentManager})")
  if (!isUnderIndicatorOrJob()) {
    throw error
  }
  else {
    throw ProcessCanceledException(error)
  }
}

