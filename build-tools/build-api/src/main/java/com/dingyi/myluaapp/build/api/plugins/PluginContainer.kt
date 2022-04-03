package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Plugin

interface PluginContainer {

    fun hasPlugin(id:String):Boolean

    fun hasPlugin(plugin:Class<Plugin<*>>):Boolean

    fun getPlugin(id:String):Class<Plugin<*>>

    fun createPlugin(id:String):Plugin<*>

    fun setPlugin(id:String,plugin:Plugin<*>)

    fun clearPlugin()
}