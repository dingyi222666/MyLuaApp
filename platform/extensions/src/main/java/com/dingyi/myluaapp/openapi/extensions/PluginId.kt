// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

import com.intellij.ReviseWhenPortedToJDK
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap

/**
 * Represents an ID of a plugin. A full descriptor of the plugin may be obtained
 * via [com.intellij.ide.plugins.PluginManagerCore.getPlugin] method.
 */
class PluginId private constructor(val idString: String) : Comparable<PluginId> {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is PluginId) return false
        return idString == o.idString
    }

    override fun hashCode(): Int {
        return idString.hashCode()
    }

    override fun compareTo(o: PluginId): Int {
        return idString.compareTo(o.idString)
    }

    override fun toString(): String {
        return idString
    }

    companion object {
        @JvmField
        val EMPTY_ARRAY = arrayOfNulls<PluginId>(0)
        private val registeredIds: MutableMap<String, PluginId> = ConcurrentHashMap()
        @JvmStatic
        fun getId(idString: String): PluginId {
            return registeredIds.computeIfAbsent(idString) { idString: String -> PluginId(idString) }
        }

        fun findId(idString: String): PluginId? {
            return registeredIds[idString]
        }

        fun findId(vararg idStrings: String): PluginId? {
            for (idString in idStrings) {
                val pluginId = registeredIds[idString]
                if (pluginId != null) {
                    return pluginId
                }
            }
            return null
        }

        @ReviseWhenPortedToJDK(value = "10", description = "Collectors.toUnmodifiableSet()")
        fun getRegisteredIds(): Set<PluginId> {
            return Collections.unmodifiableSet(HashSet(registeredIds.values))
        }
    }
}