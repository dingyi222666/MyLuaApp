package com.dingyi.myluaapp.ide.plugins



import com.dingyi.myluaapp.ide.ui.android.bundle.AndroidBundle
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.intellij.util.text.VersionComparatorUtil
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.TestOnly
import java.nio.file.Path
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

@ApiStatus.Internal
class PluginLoadingResult(
    private val brokenPluginVersions: Map<PluginId, Set<String?>>,
    @JvmField val productBuildNumber: Supplier<Int>) {
    @JvmField
    val incompletePlugins = ConcurrentHashMap<PluginId, PluginDescriptorImpl>()
    private val plugins = HashMap<PluginId, PluginDescriptorImpl>()

    // only read is concurrent, write from the only thread
    @JvmField
    val idMap = ConcurrentHashMap<PluginId, PluginDescriptorImpl>()
    @JvmField
    var duplicateModuleMap: MutableMap<PluginId, MutableList<PluginDescriptorImpl>>? = null
    private val pluginErrors =
        ConcurrentHashMap<PluginId, RuntimeException /*PluginLoadingError*/>()
    private val globalErrors = Collections.synchronizedList(ArrayList<Supplier<String>>())
    @JvmField
    val shadowedBundledIds = HashSet<PluginId>()

    // result, after calling finishLoading
    private var enabledPlugins: List<PluginDescriptorImpl>? = null

    @get:TestOnly
    val hasPluginErrors: Boolean
        get() = !pluginErrors.isEmpty()

    fun getEnabledPlugins(): List<PluginDescriptorImpl> = enabledPlugins!!

    fun enabledPluginCount() = plugins.size

    fun finishLoading() {
        val enabledPlugins = plugins.values.toTypedArray()
        plugins.clear()
        Arrays.sort(enabledPlugins, Comparator.comparing { it.pluginId })
        @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
        this.enabledPlugins = Arrays.asList(*enabledPlugins)
    }

    fun isBroken(id: PluginId): Boolean {
        val set = brokenPluginVersions.get(id) ?: return false
        val descriptor = idMap.get(id)
        return (descriptor != null) && set.contains(descriptor.version)
    }

    internal fun getPluginErrors(): Map<PluginId, RuntimeException> =
        Collections.unmodifiableMap(pluginErrors)

    fun getGlobalErrors(): List<Supplier<String>> {
        synchronized(globalErrors) {
            return java.util.ArrayList(globalErrors)
        }
    }

    internal fun addIncompletePlugin(plugin: PluginDescriptorImpl, error: RuntimeException?) {
        if (!idMap.containsKey(plugin.pluginId)) {
            incompletePlugins.put(plugin.pluginId, plugin)
        }
        if (error != null) {
            pluginErrors.put(plugin.pluginId, error)
        }
    }

    internal fun reportIncompatiblePlugin(plugin: PluginDescriptorImpl, error: RuntimeException) {
        // do not report if some compatible plugin were already added
        // no race condition here: plugins from classpath are loaded before and not in parallel to loading from plugin dir
        if (!idMap.containsKey(plugin.pluginId)) {
            pluginErrors.put(plugin.pluginId, error)
        }
    }

    fun reportCannotLoad(file: Path, e: Throwable?) {
        PluginManagerCore.logger.warn("Cannot load $file", e)
        globalErrors.add(Supplier {
            AndroidBundle.coreBundle.message(
                com.dingyi.myluaapp.ide.core.R.string.plugin_loading_error_text_file_contains_invalid_plugin_descriptor,
                pluginPathToUserString(file)
            )
        })
    }

    fun add(descriptor: PluginDescriptorImpl, overrideUseIfCompatible: Boolean): Boolean {
        val pluginId = descriptor.pluginId
       /* if (descriptor.isIncomplete) {
            return true
        }

        if (!descriptor.isBundled) {
            if (checkModuleDependencies && !PluginManagerCore.hasModuleDependencies(descriptor)) {
                val error = PluginLoadingError(
                    plugin = descriptor,
                    detailedMessageSupplier = {
                        CoreBundle.message(
                            "plugin.loading.error.long.compatible.with.intellij.idea.only",
                            descriptor.name
                        )
                    },
                    shortMessageSupplier = { CoreBundle.message("plugin.loading.error.short.compatible.with.intellij.idea.only") },
                    isNotifyUser = true
                )
                addIncompletePlugin(descriptor, error)
                return false
            }
        }*/

        // remove any error that occurred for plugin with the same id
        pluginErrors.remove(pluginId)
        incompletePlugins.remove(pluginId)
        val prevDescriptor = plugins.put(pluginId, descriptor)
        if (prevDescriptor == null) {
            idMap.put(pluginId, descriptor)
            return true
        }


        if (isCompatible(descriptor) &&
            (overrideUseIfCompatible || VersionComparatorUtil.compare(
                descriptor.version,
                prevDescriptor.version
            ) > 0)
        ) {
            PluginManagerCore.logger
                .info("${descriptor.pluginPath} overrides ${prevDescriptor.pluginPath}")
            idMap.put(pluginId, descriptor)
            return true
        } else {
            plugins.put(pluginId, prevDescriptor)
            return false
        }
    }

    private fun isCompatible(descriptor: PluginDescriptorImpl): Boolean {
        return PluginManagerCore.checkBuildNumberCompatibility(
            descriptor,
            productBuildNumber.get()
        ) == null
    }

    private fun checkAndAdd(descriptor: PluginDescriptorImpl, id: PluginId) {
        duplicateModuleMap?.get(id)?.let { duplicates ->
            duplicates.add(descriptor)
            return
        }

        val existingDescriptor = idMap.put(id, descriptor) ?: return

        // if duplicated, both are removed
        idMap.remove(id)
        if (duplicateModuleMap == null) {
            duplicateModuleMap = LinkedHashMap()
        }
        val list = ArrayList<PluginDescriptorImpl>(2)
        list.add(existingDescriptor)
        list.add(descriptor)
        duplicateModuleMap?.put(id, list)
    }
}