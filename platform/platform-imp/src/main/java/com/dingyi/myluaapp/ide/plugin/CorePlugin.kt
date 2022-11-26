package com.dingyi.myluaapp.ide.plugin

import com.dingyi.myluaapp.openapi.actions.ActionManager
import com.dingyi.myluaapp.openapi.actions.internal.ActionManagerImpl
import com.dingyi.myluaapp.openapi.dsl.plugin.PluginConfig
import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.plugin
import com.dingyi.myluaapp.openapi.dsl.plugin.service.services

class CorePlugin : PluginConfig {
    override fun config() = plugin("CorePlugin") {
        version = ""
        author = "dingyi"
        description = "Core Plugin"
        id = "com.dingyi.myluaapp"

        services {
            application("com.dingyi.myluaapp.util.messages.MessageBusFactory") impl "com.dingyi.myluaapp.util.messages.impl.MessageBusFactoryImpl"
            application(ActionManager::class.java) impl (ActionManagerImpl::class.java)
        }
    }
}