package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.openapi.extensions.PluginId


data class PluginManagerState internal constructor(
    val sortedPlugins: Array<PluginDescriptorImpl>,
    val sortedEnabledPlugins: List<PluginDescriptorImpl>,
    val disabledRequiredIds: Set<PluginId>,
    val effectiveDisabledIds: Set<PluginId>,
    val idMap: Map<PluginId, PluginDescriptorImpl>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginManagerState

        if (!sortedPlugins.contentEquals(other.sortedPlugins)) return false
        if (sortedEnabledPlugins != other.sortedEnabledPlugins) return false
        if (disabledRequiredIds != other.disabledRequiredIds) return false
        if (effectiveDisabledIds != other.effectiveDisabledIds) return false
        if (idMap != other.idMap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sortedPlugins.contentHashCode()
        result = 31 * result + sortedEnabledPlugins.hashCode()
        result = 31 * result + disabledRequiredIds.hashCode()
        result = 31 * result + effectiveDisabledIds.hashCode()
        result = 31 * result + idMap.hashCode()
        return result
    }

}
