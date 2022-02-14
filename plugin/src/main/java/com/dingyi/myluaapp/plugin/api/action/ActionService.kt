package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.Action

interface ActionService {



    fun createActionArgument():ActionArgument

    fun registerAction(actionClass:Class<Action<*>>, key:ActionKey)

    fun clearAction(key: ActionKey)

    fun <T> callAction(actionArgument: ActionArgument, key: ActionKey): T?

}