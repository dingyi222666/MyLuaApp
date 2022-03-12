package com.dingyi.myluaapp.plugin.runtime.plugin

import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.convertObject
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.loader.ApkClassLoader
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperBasePluginContext
import com.google.gson.Gson
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File

class PluginManager(private val context: PluginContext) {


    private val allPlugin = mutableListOf<Pair<Plugin,String>>()

    private val loadPlugin = mutableMapOf<Plugin,PluginContext>()


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
            val pluginId = it["pluginId"]

            if (pluginClass == null) {
                error("Missing plugin class")
            }


            val plugin = if (pluginId == "system") {
                Class.forName(pluginClass).newInstance()
            } else {
                val pluginPath = File(Paths.pluginDir, pluginId)
                val classLoader =
                    ApkClassLoader(File(pluginPath, "plugin.apk"), File(pluginPath, "lib"))
                classLoader.loadClass(pluginClass).newInstance()
            }

            if (plugin is Plugin) {
                allPlugin.add(plugin to "")
            } else {
                error("Unable to load plugin $pluginId.The parent class of the current class is not Plugin")
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
                    loadPlugin[plugin] = WrapperBasePluginContext(context, plugin)
                    plugin.onStart(loadPlugin.getValue(plugin))
                }
            }
    }

    suspend fun uninstallPlugin(pluginId: String) = withContext(Dispatchers.IO) {

    }


    suspend fun installPlugin(pluginPath: String) = withContext(Dispatchers.IO) {
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


        zipFile.extractAll(Paths.pluginDir + "/" + targetPluginId)

        file.copyTo(File(Paths.pluginDir + "/" + targetPluginId + "/plugin.apk"))

        zipFile.close()

        val pluginConfigPath = File(Paths.pluginDir, "plugin.json")

        val pluginConfigList = Gson()
            .fromJson(
                pluginConfigPath.readText(),
                getJavaClass<List<Map<String, String>>>()
            ).toMutableList()

        pluginConfigList.removeIf { it["pluginId"] == configList["pluginId"] }

        pluginConfigList.add(configList)

        pluginConfigPath.writeText(Gson().toJson(pluginConfigList))
    }

    fun stop() {
        loadPlugin.forEach { (plugin, context) ->
            allPlugin.removeIf { it.first == plugin }
            plugin.onStop(context)
        }
        loadPlugin.clear()
    }

    fun getPluginPath(plugin: Plugin): Any {
        TODO("Not yet implemented")
    }
}