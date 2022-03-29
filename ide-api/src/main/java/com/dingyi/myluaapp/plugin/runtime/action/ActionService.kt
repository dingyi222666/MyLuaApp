package com.dingyi.myluaapp.plugin.runtime.action

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.dingyi.myluaapp.common.ktx.convertObject
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.action.ActionKey
import com.dingyi.myluaapp.plugin.api.action.ActionService
import com.dingyi.myluaapp.plugin.api.action.ForwardArgument
import com.dingyi.myluaapp.plugin.api.context.PluginContext

class ActionService(private val pluginContext: PluginContext) : ActionService {

    private val allAction = mutableMapOf<ActionKey, MutableList<Class<*>>>()

    private val allForwardAction =
        mutableMapOf<ActionKey, MutableList<ForwardArgument>>()

    private val filterForwardAction =
        mutableMapOf<Pair<ActionKey, Class<*>>, MutableList<ForwardArgument>>()

    override fun createActionArgument(): ActionArgument {
        return DefaultActionArgument()
    }

    override fun <T : Action<*>> registerAction(actionClass: Class<T>, key: ActionKey) {
        val keyAction = allAction.getOrDefault(key, mutableListOf())
        if (key.isRepeat().not() && keyAction.isNotEmpty()) {
            if (keyAction[0] != actionClass) {
                error("The key can not repeat add")
            }
        }

        if (!keyAction.contains(actionClass))
            keyAction.add(actionClass)

        allAction[key] = keyAction
    }

    override fun <T : Action<*>> registerAction(
        actionClass: Class<T>,
        lifecycle: Lifecycle,
        key: ActionKey
    ) {
        registerAction(actionClass, key)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    unregisterAction(actionClass.convertObject(), key)
                    source.lifecycle.removeObserver(this)
                }
            }
        })
    }

    override fun unregisterAction(actionClass: Class<Action<*>>, key: ActionKey) {
        val keyAction = allAction.getOrDefault(key, mutableListOf())
        keyAction.remove(actionClass)
        allAction[key] = keyAction
    }


    override fun clearAction(key: ActionKey) {
        allAction[key]?.clear()
    }

    override fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T? {
        val list = allAction[key]
        var firstResult: T? = null

        if (list != null) {
            for (action in list) {

                val mapKey = key to action
                val keyAction = filterForwardAction.get(mapKey)


                val targetActionArgument = if (keyAction != null) {
                    forwardActionArgument(actionArgument, key, action)
                } else {
                    forwardActionArgument(actionArgument, key)
                }

                val result = (action.newInstance() as Action<*>).callAction(targetActionArgument)
                if (result != null) {
                    if (firstResult == null) {
                        firstResult = result as T?
                    }
                }
            }
        }
        return firstResult

    }

    private fun forwardActionArgument(
        actionArgument: ActionArgument,
        key: ActionKey,
        action: Class<*>
    ): ActionArgument {
        val mapKey = key to action
        val keyAction = filterForwardAction.getOrDefault(mapKey, mutableListOf())

        filterForwardAction[mapKey] = keyAction

        return keyAction
            .fold(actionArgument) { acc: ActionArgument, function: ForwardArgument ->
                function.invoke(acc)
            }
    }

    override fun registerForwardArgument(
        key: ActionKey,
        block: ForwardArgument
    ) {
        val keyAction = allForwardAction.getOrDefault(key, mutableListOf())
        keyAction.add(block)
        allForwardAction[key] = keyAction
    }

    override fun registerForwardArgument(
        key: ActionKey,
        targetActionClass: Class<*>,
        block: ForwardArgument
    ) {
        val mapKey = key to targetActionClass
        val keyAction = filterForwardAction.getOrElse(mapKey) {
            mutableListOf()
        }
        keyAction.add(block)
        filterForwardAction[mapKey] = keyAction
    }


    override fun registerForwardArgument(
        vararg key: ActionKey,
        block: ForwardArgument
    ) {
        key.forEach {
            registerForwardArgument(it, block)
        }
    }

    override fun forwardActionArgument(
        actionArgument: ActionArgument,
        key: ActionKey
    ): ActionArgument {
        val keyAction = allForwardAction.getOrDefault(key, mutableListOf())

        allForwardAction[key] = keyAction

        return keyAction
            .fold(actionArgument) { acc: ActionArgument, function: ForwardArgument ->
                function.invoke(acc)
            }
    }

    override fun unregisterForwardArgument(
        vararg key: ActionKey,
        block: ForwardArgument
    ) {
        key.forEach {
            unregisterForwardArgument(it, block)
        }
    }

    override fun unregisterForwardArgument(
        key: ActionKey,
        block: ForwardArgument
    ) {
        val keyAction = allForwardAction.getOrDefault(key, mutableListOf())
        keyAction.remove(block)
        allForwardAction[key] = keyAction
    }

    override fun registerForwardArgument(
        vararg key: ActionKey,
        lifecycle: Lifecycle,
        block: ForwardArgument
    ) {
        key.forEach {
            registerForwardArgument(it, lifecycle, block)
        }
    }

    override fun registerForwardArgument(
        key: ActionKey,
        lifecycle: Lifecycle,
        block: ForwardArgument
    ) {
        registerForwardArgument(key, block)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    unregisterForwardArgument(key, block)
                    source.lifecycle.removeObserver(this)
                }
            }
        })
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

