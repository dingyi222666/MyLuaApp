package com.dingyi.myluaapp.plugin.api

import com.dingyi.myluaapp.plugin.api.action.ActionArgument

interface Action {

    fun callAction(argument: ActionArgument)

    val name:String

    val id:String


}