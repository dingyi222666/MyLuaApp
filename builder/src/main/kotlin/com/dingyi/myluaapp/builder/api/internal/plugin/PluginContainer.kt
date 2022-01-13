package com.dingyi.myluaapp.builder.api.internal.plugin

import com.dingyi.myluaapp.builder.api.Plugin

interface PluginContainer {

    fun registerPlugin(plugin: Plugin<*>)

    fun removePlugin(id: String)


    fun <T:Plugin<*>> getPlugin(id:String):T
}