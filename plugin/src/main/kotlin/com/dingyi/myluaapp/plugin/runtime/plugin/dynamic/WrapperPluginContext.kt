package com.dingyi.myluaapp.plugin.runtime.plugin.dynamic

import android.content.Context
import android.content.res.AssetManager
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import java.io.File

class WrapperPluginContext(
    private val pluginAndroidContext: Context,
    private val pluginContext: PluginContext,
    private val plugin:Plugin
) : WrapperBasePluginContext(pluginContext, plugin) {

    private val pluginPath = (Paths.pluginDir + '/' + plugin.pluginId).toFile()

    override fun getAssetManager(plugin: Plugin): AssetManager {
        return pluginAndroidContext
            .assets
    }

    private val wrapperPluginContext = PluginAndroidContext(
        pluginPath = File(pluginPath, "plugin.apk"),
        wrapperContext = pluginAndroidContext
    )

    override fun getAndroidContext(): Context {
        return wrapperPluginContext
    }

    override fun getPluginDir(): File {
        return pluginPath
    }
}