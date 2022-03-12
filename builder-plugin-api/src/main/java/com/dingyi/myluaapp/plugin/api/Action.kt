package com.dingyi.myluaapp.plugin.api

import com.dingyi.myluaapp.plugin.api.action.ActionArgument

interface Action<out T> {

    abstract fun callAction(argument: ActionArgument):T?

    abstract val name:String

    abstract val id:String


}