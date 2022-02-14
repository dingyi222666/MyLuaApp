package com.dingyi.myluaapp.plugin.api

import com.dingyi.myluaapp.plugin.api.action.ActionArgument

interface Action<T> {

    fun <T> callAction(argument: ActionArgument):T?

    val name:String

    val id:String


}