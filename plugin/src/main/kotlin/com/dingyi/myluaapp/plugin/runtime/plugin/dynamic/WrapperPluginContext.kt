package com.dingyi.myluaapp.plugin.runtime.plugin.dynamic

import android.content.Context
import android.content.res.AssetManager
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext

class WrapperPluginContext(
    private val pluginAndroidContext: Context,
    private val pluginContext: PluginContext,
    private val plugin:Plugin
) : WrapperBasePluginContext(pluginContext, plugin) {
    override fun getAssetManager(plugin: Plugin): AssetManager {
        return pluginAndroidContext
            .assets
    }

    override fun getAndroidContext(): Context {
        return pluginAndroidContext
    }
}