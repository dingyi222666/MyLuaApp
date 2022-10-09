// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl
import org.jetbrains.annotations.NonNls

abstract class BaseExtensionPointName<T : Any>(val name: String) {
  override fun toString(): String = name

  protected fun getPointImpl(areaInstance: AreaInstance?): ExtensionPointImpl<T> {
    val area = (areaInstance?.extensionArea ?: Extensions.getRootArea()) as ExtensionsAreaImpl
    return area.getExtensionPoint(name)
  }
}