// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions




class ExtensionDescriptor(
    @JvmField val implementation: String?,
    @JvmField val orderId: String?,
    @JvmField val order: LoadingOrder
) {

}


class ExtensionPointDescriptor(@JvmField val name: String,
                               @JvmField val isNameQualified: Boolean,
                               @JvmField val className: String,
                               @JvmField val isBean: Boolean,
                               @JvmField val isDynamic: Boolean) {
  fun getQualifiedName(pluginDescriptor: PluginDescriptor) = if (isNameQualified) name else "${pluginDescriptor.pluginId}.$name"

  // getQualifiedName() can be used instead, but this method allows avoiding temp string creation
  fun nameEquals(qualifiedName: String, pluginDescriptor: PluginDescriptor): Boolean {
      return if (isNameQualified) {
          qualifiedName == name
      }
      else {
          val pluginId = pluginDescriptor.pluginId.idString
          (qualifiedName.length == (pluginId.length + 1 + name.length)) &&
                  qualifiedName[pluginId.length] == '.' &&
                  qualifiedName.startsWith(pluginId) &&
                  qualifiedName.endsWith(name)
      }
  }

  override fun toString(): String {
    return "ExtensionPointDescriptor(name=$name, isNameQualified=$isNameQualified, className=$className, isBean=$isBean, isDynamic=$isDynamic)"
  }
}