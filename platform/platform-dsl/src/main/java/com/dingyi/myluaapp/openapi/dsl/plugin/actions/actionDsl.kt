package com.dingyi.myluaapp.openapi.dsl.plugin.actions

import com.dingyi.myluaapp.openapi.dsl.plugin.PluginDslBuilder

class ActionsDslBuilder internal constructor() {


    val allActions = mutableListOf<ActionDslBuilder>()

    val allActionGroups = mutableListOf<ActionGroupDslBuilder>()

    fun action(text: String, block: ActionDslBuilder.() -> Unit): ActionDslBuilder {
        val builder = ActionDslBuilder(text).also(block)
        allActions.add(builder)
        return builder
    }

    fun actionGroup(text: String, block: ActionGroupDslBuilder.() -> Unit): ActionGroupDslBuilder {
        val builder = ActionGroupDslBuilder(text).also(block)
        allActionGroups.add(builder)
        return builder
    }
}

class ActionDslBuilder internal constructor(var text: String) {
    lateinit var id: String
    var targetClass: Any = ""
    var description = ""

    val allAddGroups = mutableListOf<String>()

    fun addToGroup(id: String) {
        allAddGroups.add(id)
    }
}

class ActionGroupDslBuilder internal constructor(var text: String) {
    lateinit var id: String

    var description = ""

    val allActions = mutableListOf<ActionDslBuilder>()
    val allAddGroups = mutableListOf<String>()
    val allActionGroups = mutableListOf<ActionGroupDslBuilder>()

    fun addToGroup(id: String) {
        allAddGroups.add(id)
    }

    fun action(text: String, block: ActionDslBuilder.() -> Unit): ActionDslBuilder {
        val builder = ActionDslBuilder(text).also(block)
        allActions.add(builder)
        return builder
    }


    fun actionGroup(text: String, block: ActionGroupDslBuilder.() -> Unit): ActionGroupDslBuilder {
        val builder = ActionGroupDslBuilder(text).also(block)
        allActionGroups.add(builder)
        return builder
    }
}


fun actions(block: ActionsDslBuilder.() -> Unit): ActionsDslBuilder {
    return ActionsDslBuilder().also(block)
}

val func = ::actions

fun PluginDslBuilder.actions(block: ActionsDslBuilder.() -> Unit) {
    actions = func(block)
}


