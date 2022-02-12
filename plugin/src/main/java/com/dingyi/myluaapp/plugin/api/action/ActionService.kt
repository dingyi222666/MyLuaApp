package com.dingyi.myluaapp.plugin.api.action

import com.dingyi.myluaapp.plugin.api.Action

interface ActionService {


    /**
     * 获取到默认的动作传参容器，推荐使用该方法来调用动作
     */
    fun getDefaultActionArgument(): ActionArgument

    fun createActionArgument():ActionArgument

    fun registerAction(actionClass:Class<Action>, eventId:Int)

    fun callAction(actionArgument: ActionArgument, eventId: Int)
}