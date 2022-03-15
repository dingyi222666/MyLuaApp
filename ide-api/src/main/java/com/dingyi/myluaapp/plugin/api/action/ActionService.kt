package com.dingyi.myluaapp.plugin.api.action

import androidx.lifecycle.Lifecycle
import com.dingyi.myluaapp.plugin.api.Action

interface ActionService {


    fun createActionArgument(): ActionArgument

    fun <T : Action<*>> registerAction(actionClass: Class<T>, key: ActionKey)

    /**
     * Register an action,when lifecycle destroy,will automatic unregister the action
     */
    fun <T : Action<*>> registerAction(actionClass: Class<T>, lifecycle: Lifecycle, key: ActionKey)

    /**
     * Unregister an action
     */
    fun unregisterAction(actionClass: Class<Action<*>>, key: ActionKey)

    fun clearAction(key: ActionKey)

    fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T?

    fun forwardActionArgument(actionArgument: ActionArgument, key: ActionKey): ActionArgument

    /**
     * Unregister one or more forwarding event
     */
    fun unregisterForwardArgument(vararg key: ActionKey, block: (ActionArgument) -> ActionArgument)

    /**
     *  Register forward argument event,when lifecycle destroy,will automatic unregister the forward argument
     */
    fun registerForwardArgument(
        vararg key: ActionKey,
        lifecycle: Lifecycle,
        block: (ActionArgument) -> ActionArgument
    )


    fun registerForwardArgument(key: ActionKey, block: (ActionArgument) -> ActionArgument)

    fun registerForwardArgument(vararg key: ActionKey, block: (ActionArgument) -> ActionArgument)

    /**
     * Unregister a forwarding event
     */
    fun unregisterForwardArgument(key: ActionKey, block: (ActionArgument) -> ActionArgument)

    /**
     *  Register forward argument event,when lifecycle destroy,will automatic unregister the forward argument
     */
    fun registerForwardArgument(
        key: ActionKey,
        lifecycle: Lifecycle,
        block: (ActionArgument) -> ActionArgument
    )
}