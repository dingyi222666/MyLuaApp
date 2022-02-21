package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.context.PluginContext

interface ActionArgument {

    fun addArgument(arg:Any?):ActionArgument

    fun clear()

    fun getPluginContext(): PluginContext
    fun <T> getArgument(i: Int): T?
}