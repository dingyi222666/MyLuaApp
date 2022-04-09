package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.plugins.PluginContainer
import com.dingyi.myluaapp.build.api.properties.Properties

interface BuildTool:Properties {

    fun getVersion(): String

    fun getPluginContainer(): PluginContainer

}