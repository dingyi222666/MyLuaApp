package com.dingyi.myluaapp.build.api.internal.plugins

import com.dingyi.myluaapp.build.api.plugins.PluginAware

interface PluginAwareInternal: PluginAware {
    override fun getPluginManager(): PluginManagerInternal
}