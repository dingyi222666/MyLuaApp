package com.dingyi.myluaapp.plugin.runtime.plugin

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.loader.ApkClassLoader
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperBasePluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperPluginContext
import com.google.gson.Gson
import java.io.File

class PluginContainer(
    private var pluginId: String,
    private val pluginMainClass: String,
    private val pluginConfig: MutableMap<String, String>,
    private val superPluginContext: PluginContext
) {


    val isEnabled: Boolean
        get() = enabled
    private lateinit var pluginInstance: Plugin

    private lateinit var pluginContext: PluginContext

    private var isLoader = false

    private var enabled = pluginConfig["enabled"]?.toBooleanStrictOrNull() ?: true


    fun init() {

        //load plugin

        val plugin = if (pluginId == "system") {
            MainApplication
                .instance
                .classLoader
                .loadClass(pluginMainClass)
                .newInstance() as Plugin
        } else {
            val pluginPath = File(Paths.pluginDir, pluginId)
            val classLoader =
                ApkClassLoader(
                    File(pluginPath, "plugin.apk"), File(pluginPath, "lib"), MainApplication
                        .instance.classLoader
                )
            classLoader.loadClass(pluginMainClass).newInstance()
        }

        if (plugin is Plugin) {
            pluginInstance = plugin
        } else {
            error("Unable to load plugin $pluginId.The parent class of the current class is not Plugin")
        }

        pluginId = pluginInstance
            .pluginId

    }

    private fun initContext() {

        if (this::pluginContext.isInitialized) {
            return
        }

        val pluginBaseId = pluginConfig["pluginId"]

        pluginContext = if (pluginBaseId == "system") {
            WrapperBasePluginContext(superPluginContext, pluginInstance)
        } else {
            WrapperPluginContext(
                pluginContext = superPluginContext,
                plugin = pluginInstance,
                pluginAndroidContext = MainApplication.instance
            )
        }

    }

    fun insertPluginToList() {

        val pluginConfigPath = File(Paths.pluginDir, "plugin.json")

        val pluginConfigList = Gson()
            .fromJson(
                pluginConfigPath.readText(),
                getJavaClass<List<Map<String, String>>>()
            ).toMutableList()

        pluginConfigList.removeIf { it["pluginId"] == pluginConfig["pluginId"] }

        pluginConfigList.add(pluginConfig)

        pluginConfigPath.writeText(Gson().toJson(pluginConfigList))

    }

    fun removePluginToList() {
        val pluginConfigPath = File(Paths.pluginDir, "plugin.json")

        val pluginConfigList = Gson()
            .fromJson(
                pluginConfigPath.readText(),
                getJavaClass<List<Map<String, String>>>()
            ).toMutableList()

        pluginConfigList.removeIf { it["pluginId"] == pluginConfig["pluginId"] }

        pluginConfigPath.writeText(Gson().toJson(pluginConfigList))

    }

    fun toggle() {
        enabled = enabled.not()
    }

    fun start() {

        isLoader = true

        initContext()



        pluginInstance
            .onStart(pluginContext)
    }

    fun stop() {

        isLoader = false

        pluginInstance
            .onStop(pluginContext)
    }


    fun install() {

        initContext()

        pluginInstance
            .onInstall(pluginContext)
    }

    fun uninstall() {

        initContext()

        pluginInstance
            .onUninstall(pluginContext)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginContainer

        if (pluginId != other.pluginId) return false

        return true
    }

    override fun hashCode(): Int {
        return pluginId.hashCode()
    }

    fun getPlugin() = pluginInstance
    fun getPluginId() = pluginId

}