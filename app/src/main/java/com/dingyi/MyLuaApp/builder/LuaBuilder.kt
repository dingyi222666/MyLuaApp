package com.dingyi.MyLuaApp.builder

import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo

class LuaBuilder :IBuilder {

    override fun getName(): String {
        return "LuaBuilder"
    }

    override fun initActivity(activity: BaseActivity): IBuilder {
        return this
    }


    override fun run() {

    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun initBuilderOut(builderOut: IBuilderOut): IBuilder {

        return this
    }

    override fun initProjectInfo(projectInfo: ProjectInfo): IBuilder {

        return this
    }

}