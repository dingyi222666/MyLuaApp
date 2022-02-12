package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.context.PluginContext

interface ActionArgument {
    fun <T> getArgument():T

    fun <T> addArgument(arg:T)

    fun clear()

    fun getPluginContext(): PluginContext
}