package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.ide.plugins.PluginManagerCore.writePluginsList
import com.dingyi.myluaapp.openapi.application.ApplicationInfo
import com.dingyi.myluaapp.openapi.application.PathManager
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.intellij.openapi.application.JetBrainsProtocolHandler
import com.intellij.openapi.util.io.NioFiles
import org.jetbrains.annotations.TestOnly
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Arrays
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList


object DisabledPluginsState {

    val DISABLED_PLUGINS_FILENAME = "disabled_plugins.txt"


    @Volatile
    private var ourDisabledPlugins: MutableSet<PluginId>? = null
    private val ourDisabledPluginListeners: MutableList<Runnable> = CopyOnWriteArrayList()

    @Volatile
    private var ourIgnoreDisabledPlugins = false


    fun addDisablePluginListener(listener: Runnable) {
        ourDisabledPluginListeners.add(listener)
    }


    fun removeDisablePluginListener(listener: Runnable) {
        ourDisabledPluginListeners.remove(listener)
    }

    // For use in headless environment only

    fun setIgnoreDisabledPlugins(ignoreDisabledPlugins: Boolean) {
        ourIgnoreDisabledPlugins = ignoreDisabledPlugins
    }

    fun loadDisabledPlugins(): MutableSet<PluginId> {
        val disabledPlugins: MutableSet<PluginId> = LinkedHashSet()
        val file: Path = Paths.get(PathManager.configPath.toString(), DISABLED_PLUGINS_FILENAME)
        if (!Files.isRegularFile(file)) {
            return disabledPlugins
        }
        val applicationInfo = ApplicationInfo.instance
        val requiredPlugins = splitByComma(JetBrainsProtocolHandler.REQUIRED_PLUGINS_KEY)
        var updateDisablePluginsList = false
        try {
            Files.newBufferedReader(file).use { reader ->
                var id: String
                while (reader.readLine().also { id = it } != null) {
                    id = id.trim { it <= ' ' }
                    if (id.isEmpty()) {
                        continue
                    }
                    if (!requiredPlugins.contains(id) && !applicationInfo.isEssentialPlugin(id)) {
                        addIdTo(id, disabledPlugins)
                    } else {
                        updateDisablePluginsList = true
                    }
                }
                for (suppressedId in getNonEssentialSuppressedPlugins(
                    applicationInfo
                )) {
                    if (addIdTo(suppressedId, disabledPlugins)) {
                        updateDisablePluginsList = true
                    }
                }
            }
        } catch (e: IOException) {
            logger.info("Unable to load disabled plugins list from $file", e)
        } finally {
            if (updateDisablePluginsList) {
                trySaveDisabledPlugins(file, disabledPlugins, false)
            }
        }
        return disabledPlugins
    }


    fun disabledPlugins(): Set<PluginId> {
        return Collections.unmodifiableSet(disabledIds)
    }

    // to preserve the order of additions and removals

    private val disabledIds: MutableSet<PluginId>?
        private get() {
            var result: MutableSet<PluginId>? = ourDisabledPlugins
            if (result != null) {
                return result
            }

            // to preserve the order of additions and removals
            if (ourIgnoreDisabledPlugins || System.getProperty("idea.ignore.disabled.plugins") != null) {
                return HashSet()
            }
            synchronized(DisabledPluginsState::class.java) {
                result = ourDisabledPlugins
                if (result == null) {
                    result = loadDisabledPlugins()
                    ourDisabledPlugins = result
                }
                return result
            }
        }


    fun isDisabled(pluginId: PluginId): Boolean {
        return disabledIds!!.contains(pluginId)
    }


    fun setEnabledState(
        plugins: Set<PluginId>,
        enabled: Boolean
    ): Boolean {
        val disabled: MutableSet<PluginId>? = disabledIds
        val changed = if (enabled) disabled?.removeAll(plugins) else disabled?.addAll(plugins)
        logger.info(joinedPluginIds(plugins, enabled))
        return changed == true && trySaveDisabledPlugins(disabled.checkNotNull())
    }


    fun trySaveDisabledPlugins(pluginIds: Collection<PluginId>): Boolean {
        return trySaveDisabledPlugins(
            PathManager.configPath.resolve(DISABLED_PLUGINS_FILENAME),
            pluginIds,
            true
        )
    }

    private fun trySaveDisabledPlugins(
        file: Path,
        pluginIds: Collection<PluginId>,
        invalidate: Boolean
    ): Boolean {
        return try {
            saveDisabledPlugins(file, pluginIds, invalidate)
            true
        } catch (e: IOException) {
            logger.warn("Unable to save disabled plugins list", e)
            false
        }
    }

    @TestOnly
    fun saveDisabledPlugins(configDir: Path, vararg ids: String) {
        val pluginIds: MutableList<PluginId> = ArrayList()
        for (id in ids) {
            addIdTo(id, pluginIds)
        }
        saveDisabledPlugins(configDir.resolve(DISABLED_PLUGINS_FILENAME), pluginIds, true)
    }


    private fun saveDisabledPlugins(
        file: Path,
        pluginIds: Collection<PluginId>?,
        invalidate: Boolean
    ) {
        savePluginsList(pluginIds, file)
        if (invalidate) {
            invalidate()
        }
        for (listener in ourDisabledPluginListeners) {
            listener.run()
        }
    }


    fun savePluginsList(ids: Collection<PluginId>?, file: Path) {
        NioFiles.createDirectories(file.getParent())
        Files.newBufferedWriter(file).use { writer ->
            writePluginsList(
                ids!!, writer
            )
        }
    }

    // do not use class reference here

    private val logger: Logger
        private get() =// do not use class reference here
            Logger.getInstance("#com.intellij.ide.plugins.DisabledPluginsState")

    fun invalidate() {
        ourDisabledPlugins = null
    }

    private fun addIdTo(
        id: String,
        pluginIds: MutableCollection<PluginId>
    ): Boolean {
        return pluginIds.add(PluginId.getId(id))
    }


    private fun getNonEssentialSuppressedPlugins(applicationInfo: ApplicationInfo): List<String> {
        val suppressedPlugins = splitByComma("idea.suppressed.plugins.id")
        if (suppressedPlugins.isEmpty()) {
            return Collections.emptyList()
        }
        val result: MutableList<String> = ArrayList(suppressedPlugins.size)
        for (suppressedPlugin in suppressedPlugins) {
            if (!applicationInfo.isEssentialPlugin(suppressedPlugin)) {
                result.add(suppressedPlugin)
            }
        }
        return result
    }


    private fun splitByComma(key: String): List<String> {
        val strings =
            System.getProperty(key, "").split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        return if (strings.isEmpty() || strings.size == 1 && strings[0].isEmpty()) Collections.emptyList() else
            mutableListOf(key)
    }


    private fun joinedPluginIds(
        pluginIds: Collection<PluginId>,
        enabled: Boolean
    ): String {
        val buffer = StringBuilder("Plugins to ")
            .append(if (enabled) "enable" else "disable")
            .append(": [")
        val iterator: Iterator<PluginId> = pluginIds.iterator()
        while (iterator.hasNext()) {
            buffer.append(iterator.next().idString)
            if (iterator.hasNext()) {
                buffer.append(", ")
            }
        }
        return buffer.append(']').toString()
    }
}
