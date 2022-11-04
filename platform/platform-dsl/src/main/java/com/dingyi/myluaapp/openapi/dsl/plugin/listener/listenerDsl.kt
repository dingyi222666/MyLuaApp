package com.dingyi.myluaapp.openapi.dsl.plugin.listener

import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder

class ListenerDslBuilder internal constructor(level: Level) {

    val allListener = mutableListOf<ListenerImplementationBuilder>()

    enum class Level {
        Application, PROJECT
    }


    fun listener(
        block: ListenerImplementationBuilder.() -> Unit
    ): ListenerImplementationBuilder {
        val builder = ListenerImplementationBuilder().also(block)
        allListener.add(builder)
        return builder
    }
}


class ListenerImplementationBuilder() {
    var targetClass: Any = ""
    var topic: String = ""
}

fun listeners(
    level: ListenerDslBuilder.Level,
    block: ListenerDslBuilder.() -> Unit
): ListenerDslBuilder {
    val builder = ListenerDslBuilder(level).also(block)
    return builder
}

val func = ::listeners

fun PluginDslBuilder.listeners(
    level: ListenerDslBuilder.Level,
    block: ListenerDslBuilder.() -> Unit
) {
    if (!isInitializedListeners()) {
        this.listeners = mutableListOf()
    }
    listeners.add(func(level, block))
}
