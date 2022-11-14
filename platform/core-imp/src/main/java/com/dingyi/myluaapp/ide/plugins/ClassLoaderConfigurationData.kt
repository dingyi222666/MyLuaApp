package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.openapi.extensions.PluginId


object ClassLoaderConfigurationData {
    val SEPARATE_CLASSLOADER_FOR_SUB = java.lang.Boolean.parseBoolean(
        System.getProperty(
            "idea.classloader.per.descriptor",
            "true"
        )
    )
    var SEPARATE_CLASSLOADER_FOR_SUB_ONLY: MutableSet<PluginId>
    var SEPARATE_CLASSLOADER_FOR_SUB_EXCLUDE: MutableSet<PluginId>

    init {
        val value = System.getProperty("idea.classloader.per.descriptor.only")
        if (value == null) {
            SEPARATE_CLASSLOADER_FOR_SUB_ONLY = mutableSetOf(
                    PluginId.getId("org.jetbrains.plugins.ruby"),
                    PluginId.getId("PythonCore"),
                    PluginId.getId("com.jetbrains.rubymine.customization"),
                    PluginId.getId("JavaScript"),
                    PluginId.getId("Docker"),
                    PluginId.getId("com.intellij.diagram")
                )
           /* )*/
        } else if (value.isEmpty()) {
            SEPARATE_CLASSLOADER_FOR_SUB_ONLY = mutableSetOf<PluginId>()
        } else {
            SEPARATE_CLASSLOADER_FOR_SUB_ONLY =
               mutableSetOf<PluginId>()
            for (id in value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                SEPARATE_CLASSLOADER_FOR_SUB_ONLY.add(PluginId.getId(id))
            }
        }
        SEPARATE_CLASSLOADER_FOR_SUB_EXCLUDE = mutableSetOf(
           /* arrayOf<PluginId>(*/
                PluginId.getId("org.jetbrains.kotlin"),
                PluginId.getId("com.intellij.java"),
                PluginId.getId("com.intellij.spring.batch"),
                PluginId.getId("com.intellij.spring.integration"),
                PluginId.getId("com.intellij.spring.messaging"),
                PluginId.getId("com.intellij.spring.ws"),
                PluginId.getId("com.intellij.spring.websocket"),
                PluginId.getId("com.intellij.spring.webflow"),
                PluginId.getId("com.intellij.spring.security"),
                PluginId.getId("com.intellij.spring.osgi"),
                PluginId.getId("com.intellij.spring.mvc"),
                PluginId.getId("com.intellij.spring.data"),
                PluginId.getId("com.intellij.spring.boot.run.tests"),
                PluginId.getId("com.intellij.spring.boot"),
                PluginId.getId("com.jetbrains.space"),
                PluginId.getId("com.intellij.spring")
            /*)*/
        )
    }

    fun isClassloaderPerDescriptorEnabled(pluginId: PluginId, packagePrefix: String?): Boolean {
        return if (!SEPARATE_CLASSLOADER_FOR_SUB || SEPARATE_CLASSLOADER_FOR_SUB_EXCLUDE.contains(
                pluginId
            )
        ) {
            false
        } else packagePrefix != null ||
                SEPARATE_CLASSLOADER_FOR_SUB_ONLY.isEmpty() ||
                SEPARATE_CLASSLOADER_FOR_SUB_ONLY.contains(pluginId)
    }
}
