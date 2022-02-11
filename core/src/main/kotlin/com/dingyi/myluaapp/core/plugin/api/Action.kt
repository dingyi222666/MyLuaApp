package com.dingyi.myluaapp.core.plugin.api

import com.dingyi.myluaapp.core.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.core.plugin.api.context.PluginContext

interface Action {

    fun callAction(argument: ActionArgument)

    val name:String

    val id:String


}