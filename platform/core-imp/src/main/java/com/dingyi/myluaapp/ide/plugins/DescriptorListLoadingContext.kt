package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.annotations.ApiStatus
import java.util.ArrayList
import java.util.Arrays
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue


private val unitTestWithBundledPlugins = java.lang.Boolean.getBoolean("idea.run.tests.with.bundled.plugins")

private val LOG: Logger
    get() = PluginManagerCore.logger

@ApiStatus.Internal
class DescriptorListLoadingContext constructor(
    @JvmField val disabledPlugins: Set<PluginId>,
    @JvmField val result: PluginLoadingResult = createPluginLoadingResult(null),
    checkOptionalConfigFileUniqueness: Boolean = false,
    @JvmField val transient: Boolean = false
)  {


    @Volatile var defaultVersion: Int? = null
        get() {
            var result = field
            if (result == null) {
                result = this.result.productBuildNumber.get()
                field = result
            }
            return result
        }
        private set


    private val optionalConfigNames: MutableMap<String, PluginId>? = if (checkOptionalConfigFileUniqueness) ConcurrentHashMap() else null

    fun isPluginDisabled(id: PluginId): Boolean {
        return PluginManagerCore.CORE_ID != id && disabledPlugins.contains(id)
    }


    fun checkOptionalConfigShortName(configFile: String, descriptor: PluginDescriptor): Boolean {
        val pluginId = descriptor.pluginId ?: return false
        val configNames = optionalConfigNames
        if (configNames == null || configFile.startsWith("myluaapp.")) {
            return false
        }

        val oldPluginId = configNames.put(configFile, pluginId)
        if (oldPluginId == null || oldPluginId == pluginId) {
            return false
        }

        LOG.error("Optional config file with name $configFile already registered by $oldPluginId. " +
                "Please rename to ensure that lookup in the classloader by short name returns correct optional config. " +
                "Current plugin: $descriptor.")
        return true
    }
}


