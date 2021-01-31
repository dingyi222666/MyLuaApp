package com.dingyi.MyLuaApp.builder.task

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.builder.IBuilderOut

interface ITask {
    fun doAction(vararg arg:Any);
    fun initBuilderOut(builderOut:IBuilderOut):ITask;
    fun initActivity(activity: BaseActivity):ITask;
    fun sendMessage(string: String)
    fun sendError(string: String)
}