package com.dingyi.MyLuaApp.builder

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo

interface IBuilder {
    fun getName():String;//获取名字
    fun initActivity(activity: BaseActivity):IBuilder;
    suspend fun run()//builder
    fun start();
    fun initBuilderOut(builderOut: IBuilderOut):IBuilder
    fun initProjectInfo(projectInfo: ProjectInfo):IBuilder;
}