package com.dingyi.myluaapp.util.messages

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor

data class ListenerDescriptor(
    val listenerClassName: String,
    val topicClassName: String,
    val pluginDescriptor: PluginDescriptor

)
