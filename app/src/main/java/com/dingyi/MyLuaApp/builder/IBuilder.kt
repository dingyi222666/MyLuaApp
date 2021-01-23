package com.dingyi.MyLuaApp.builder

import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo

interface IBuilder {
    fun getName():String;//获取builder的名字
    fun initActivity(activity: BaseActivity):IBuilder;
    fun run()//运行builder
    fun initBuilderOut(builderOut: IBuilderOut):IBuilder
    fun initProjectInfo(projectInfo: ProjectInfo):IBuilder;
}