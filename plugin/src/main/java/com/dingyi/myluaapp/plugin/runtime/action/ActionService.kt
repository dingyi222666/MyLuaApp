package com.dingyi.myluaapp.plugin.runtime.action

import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.action.ActionKey
import com.dingyi.myluaapp.plugin.api.action.ActionService
import com.dingyi.myluaapp.plugin.api.context.PluginContext

class ActionService(private val pluginContext: PluginContext) : ActionService {

    private val allAction = mutableMapOf<ActionKey, MutableList<Class<*>>>()

    override fun createActionArgument(): ActionArgument {
        return DefaultActionArgument()
    }

    override fun <T : Action<*>> registerAction(actionClass: Class<T>, key: ActionKey) {
        val keyAction = allAction.getOrDefault(key, mutableListOf())
        if (key.isRepeat().not() && keyAction.isNotEmpty()) {
            error("The key can not repeat add")
        }
        keyAction.add(actionClass)
        allAction[key] = keyAction
    }


    override fun clearAction(key: ActionKey) {
        allAction[key]?.clear()
    }

    override fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T? {
        val list = allAction[key]

        if (list != null) {
            for (action in list) {
                val result = (action.newInstance() as Action<*>).callAction(actionArgument)
                if (result != null) {
                    return result as T
                }
            }
        }
        return null

    }

    private inner class DefaultActionArgument : ActionArgument {

        private val argument = mutableListOf<Any?>()

        override fun <T> getArgument(i: Int): T? {
            return argument[i] as T?
        }

        override fun addArgument(arg: Any?): ActionArgument {
            argument.add(arg)
            return this
        }

        override fun clear() {
            argument.clear()
        }

        override fun getPluginContext(): PluginContext {
            return pluginContext
        }

    }

}