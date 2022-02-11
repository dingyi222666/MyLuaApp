package com.dingyi.myluaapp.core.plugin.runtime.plugin

import android.util.Log
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.core.plugin.api.Plugin
import com.dingyi.myluaapp.core.plugin.api.context.PluginContext
import com.google.gson.Gson
import java.io.File

class PluginManager(private val context: PluginContext) {


    private val allPlugin = mutableListOf<Plugin>()

    fun getAllPlugin() = allPlugin

    fun init() {
        val pluginConfigPath = File(Paths.pluginDir, "plugin.json")

        val configList = Gson()
            .fromJson(
                pluginConfigPath.readText(),
                getJavaClass<List<Map<String, String>>>()
            )

        configList.forEach {
            val pluginClass = it["pluginMainClass"]
            val pluginPath = it["pluginPath"]

            if (pluginClass == null) {
                error("Missing plugin class")
            }

            if (pluginPath == "null") {
                val plugin = Class.forName(pluginClass).newInstance()


                if (plugin is Plugin) {
                    allPlugin.add(plugin)
                } else {
                    error("Load PluginClass $pluginClass fail.The Class isn't extends Plugin")
                }

            }
        }

    }

    fun loadPlugin(pluginId: String) {
        allPlugin.find { it.pluginId == pluginId }
            ?.onStart(context)
    }

    fun loadAllPlugin() {
        allPlugin.forEach {
            it.onStart(context)
        }
    }

    fun uninstallPlugin(pluginId: String) {

    }

    fun installPlugin(pluginPath: String) {


    }
}