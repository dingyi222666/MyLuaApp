package com.dingyi.MyLuaApp.builder.task

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.builder.IBuilderOut

abstract class LuaTask:ITask {

    var builderOut:IBuilderOut?=null;

    var activity: BaseActivity?=null;

    //The Task Is Use To Compile Lua Task
    override fun doAction(vararg arg: Any) {
        //Task action in it
    }

    override fun initActivity(activity: BaseActivity): LuaTask {
        this.activity=activity;
        return this
    }

    override fun initBuilderOut(builderOut: IBuilderOut):  LuaTask {
        this.builderOut=builderOut;
        return this;
    }

    override fun sendMessage(string: String) {
        builderOut?.hasMessage("lua build: $string")
    }

    override fun sendError(string: String) {
        builderOut?.hasError(string)
    }
 }