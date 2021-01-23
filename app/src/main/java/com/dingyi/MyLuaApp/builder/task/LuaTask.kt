package com.dingyi.MyLuaApp.builder.task

import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.builder.IBuilderOut

abstract class LuaTask:ITask {

    var builderOut:IBuilderOut?=null;

    var activity:BaseActivity?=null;

    //The Task Is Use To Compile Lua Task
    override fun doAction(vararg arg: Any) {
        //Task action in it
    }

    override fun initActivity(activity: BaseActivity): ITask {
        this.activity=activity;
        return this
    }

    override fun initBuilderOut(builderOut: IBuilderOut): ITask {
        this.builderOut=builderOut;
        return this;
    }

    override fun sendMessage(string: String) {
        builderOut?.hasMessage("Lua Builder: {$string}")
    }

    override fun sendError(string: String) {
        builderOut?.hasError(string)
    }
 }