// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

/**
 * Extensions should implement this interface when it is important to find out what particular plugin has provided this extension.
 */
interface PluginAware {
    /**
     * Called by extensions framework when extension is loaded from plugin.xml descriptor.
     *
     * If this method is implemented in a [bean class][ExtensionPoint.Kind.BEAN_CLASS]
     * extension point and it also exposes the stored plugin description via `getPluginDescriptor` method, you **must annotate the latter
     * with [@Transient][com.intellij.util.xmlb.annotations.Transient]** to ensure that serialization engine won't try to deserialize this property.
     * @param pluginDescriptor descriptor of the plugin that provided this particular extension.
     */
    fun setPluginDescriptor(pluginDescriptor: PluginDescriptor)
}