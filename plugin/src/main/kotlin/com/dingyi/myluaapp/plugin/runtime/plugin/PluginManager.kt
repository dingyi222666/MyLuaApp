package com.dingyi.myluaapp.plugin.runtime.plugin

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.loader.ApkClassLoader
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperBasePluginContext
import com.dingyi.myluaapp.plugin.runtime.plugin.dynamic.WrapperPluginContext
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File

class PluginManager(private val context: PluginContext) {


    private val allPlugin = mutableListOf<PluginContainer>()

    private val loadedPlugin = mutableListOf<PluginContainer>()


    fun getAllPlugin() = allPlugin.map { it.getPlugin() }

    fun init() {

        val pluginConfigPath = File(Paths.pluginDir, "plugin.json")

        val configList = Gson()
            .fromJson(
                pluginConfigPath.readText(),
                getJavaClass<List<Map<String, String>>>()
            )

        configList.map {
            val pluginId = it.getValue("pluginId")
            PluginContainer(
                pluginId = pluginId,
                pluginMainClass = it.getValue("pluginMainClass"),
                pluginConfig = it.toMutableMap(),
                superPluginContext = context
            )
        }.forEach {
            allPlugin.add(it)
        }

    }

    fun loadPlugin(pluginId: String) {

        val loadPlugin = allPlugin.find { it.getPluginId() == pluginId }
            .checkNotNull()

        if (!loadedPlugin.contains(loadPlugin) || loadPlugin
                .isEnabled
        ) {
            loadPlugin.init()
            loadPlugin.start()
        }

        loadedPlugin.add(loadPlugin)
    }

    fun loadAllPlugin() {
        val notLoaderPluginList = allPlugin - loadedPlugin
        for (it in notLoaderPluginList) {
            if (!it.isEnabled) {
                continue
            }
            it.init()
            it.start()
            loadedPlugin.add(it)
        }
    }

    suspend fun uninstallPlugin(pluginId: String) = withContext(Dispatchers.IO) {

    }


    suspend fun installPlugin(pluginPath: String):Int = withContext(Dispatchers.IO) {
        var result = 0
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

        val pluginClass = configList.getValue("pluginMainClass")

        val pluginId = configList.getValue("pluginId")
        //先运行一次


        zipFile.extractFile("plugin.json", Paths.pluginDir + "/" + pluginId)

        runCatching {
            zipFile.extractFile("lib", Paths.pluginDir + "/" + pluginId)
        }

        file.copyTo(File(Paths.pluginDir + "/" + pluginId + "/plugin.apk"), overwrite = true)

        zipFile.close()

        val container = PluginContainer(
            pluginId = pluginId,
            pluginMainClass = pluginClass,
            pluginConfig = configList.toMutableMap(),
            superPluginContext = context
        )

        container.init()

        val plugin = container.getPlugin()

        val pluginDir = File(Paths.pluginDir, pluginId)

        if (plugin.pluginId != pluginId) {
            pluginDir.deleteRecursively()
            error("Unable to install plugin $pluginId.The manifest plugin id not equals plugin main class plugin id")
        }

        if (plugin.targetApiVersion < context.apiVersion) {
            result = 1
        }

        container.insertPluginToList()
        withContext(Dispatchers.Main) {
            container.install()
        }

        result
    }

    fun stop() {
        loadedPlugin.forEach { container ->
            allPlugin.remove(container)
            container.stop()
        }
        loadedPlugin.clear()
        allPlugin.clear()
    }

    fun getPluginPath(plugin: Plugin): String {
        return Paths.pluginDir + "/" + plugin.pluginId
    }
}