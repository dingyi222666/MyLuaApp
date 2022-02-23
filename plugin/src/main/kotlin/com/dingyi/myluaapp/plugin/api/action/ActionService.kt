package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.Action

interface ActionService {



    fun createActionArgument():ActionArgument

    fun <T:Action<*>> registerAction(actionClass:Class<T>, key:ActionKey)

    fun clearAction(key: ActionKey)

    fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T?

    fun forwardAction(actionArgument: ActionArgument,key: ActionKey):ActionArgument
    fun registerForwardAction(key: ActionKey, block: (ActionArgument) -> ActionArgument)
}