package com.dingyi.myluaapp.plugin.runtime.plugin

import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.convertObject
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperBasePluginContext
import com.google.gson.Gson
import dalvik.system.DexClassLoader
import net.lingala.zip4j.ZipFile
import java.io.File

class PluginManager(private val context: PluginContext) {


    private val allPlugin = mutableListOf<Pair<Plugin,String>>()

    private val loadPlugin = mutableMapOf<Plugin,PluginContext>()

    private val allPluginPath = mutableMapOf<String, String>()

    fun getAllPlugin() = allPlugin.map { it.first }

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
                    allPluginPath[plugin.pluginId] = pluginPath.toString()
                    allPlugin.add(plugin to "")
                } else {
                    error("Unable to load plugin class $pluginClass.The parent class of the current object is not  Plugin")
                }

            }
        }

    }

    fun loadPlugin(pluginId: String) {
        allPlugin.find { it.first.pluginId == pluginId }
            ?.let { (preLoadPlugin,path) -> if (loadPlugin.keys.find { it.pluginId == preLoadPlugin.pluginId } == null) preLoadPlugin to path else null }
            ?.let { (plugin,path) ->
                if (path.isEmpty()) {
                    loadPlugin[plugin] = WrapperBasePluginContext(context,plugin)
                }
                plugin.onStart(loadPlugin.getValue(plugin))
            }

    }

    fun loadAllPlugin() {
        allPlugin
            .mapNotNull { (preLoadPlugin,path) -> if (loadPlugin.keys.find { it.pluginId == preLoadPlugin.pluginId } == null) preLoadPlugin to path else null }
            .forEach { (plugin,path) ->
                if (path.isEmpty()) {
                    loadPlugin[plugin] = WrapperBasePluginContext(context,plugin)
                    plugin.onStart(loadPlugin.getValue(plugin))
                }
            }
    }

    fun uninstallPlugin(pluginId: String) {

    }

    private fun getPluginId(pluginPath: String) {

    }

    fun installPlugin(pluginPath: String) {
        val file = pluginPath.toFile()
        if (file.isFile.not()) {
            error("Unable to install plugin:${pluginPath}")
        }
        val zipFile = ZipFile(pluginPath)
        val pluginJson = zipFile.getFileHeader("plugin.json")
            .let { zipFile.getInputStream(it).readBytes().decodeToString() }


        val configList = Gson()
            .fromJson(
                pluginJson,
                getJavaClass<Map<String, String>>()
            )

        val pluginClass = configList["pluginMainClass"]

        //先运行一次

        val classLoader =
            DexClassLoader(pluginPath, Paths.dexLoaderDir, null, ClassLoader.getSystemClassLoader())

        val pluginClassInstance = classLoader.loadClass(pluginClass).convertObject<Class<Plugin>>()
            .newInstance()

        val targetPluginId = pluginClassInstance
            .pluginId

        zipFile.close()
    }

    fun stop() {
        loadPlugin.forEach { (plugin,context) ->
            allPluginPath.remove(plugin.pluginId)
            allPlugin.removeIf { it.first == plugin }
            plugin.onStop(context)
            loadPlugin.remove(plugin)
        }
    }

    fun getPluginPath(plugin: Plugin): Any {
        TODO("Not yet implemented")
    }
}