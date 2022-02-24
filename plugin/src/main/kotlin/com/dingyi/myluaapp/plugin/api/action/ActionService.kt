package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.Action

interface ActionService {



    fun createActionArgument():ActionArgument

    fun <T:Action<*>> registerAction(actionClass:Class<T>, key:ActionKey)

    fun clearAction(key: ActionKey)

    fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T?


    fun forwardActionArgument(actionArgument: ActionArgument, key: ActionKey):ActionArgument
    fun registerForwardArgument(key: ActionKey, block: (ActionArgument) -> ActionArgument)
}