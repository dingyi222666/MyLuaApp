package com.dingyi.MyLuaApp.builder

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.builder.task.lua.CompileLuaTask
import com.dingyi.MyLuaApp.builder.task.lua.CreateApkTask
import com.dingyi.MyLuaApp.builder.task.lua.InitBuildCacheTask
import com.dingyi.MyLuaApp.builder.task.lua.MergeAXMLTask
import com.dingyi.MyLuaApp.utils.e
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LuaBuilder : IBuilder {

    private var activity: BaseActivity? = null;

    private var iBuilderOut: IBuilderOut? = null;

    private var projectInfo: ProjectInfo? = null;

    private var iBuilderCache: IBuilderCache? = null;

    override fun getName(): String {
        return "LuaBuilder"
    }

    override fun initActivity(activity: BaseActivity): LuaBuilder {
        this.activity = activity;
        return this
    }


    override suspend fun run() {
        iBuilderCache = LuaBuilderCache(projectInfo!!)

        InitBuildCacheTask()
                .initBuilderOut(iBuilderOut!!)
                .initActivity(activity!!).doAction(iBuilderCache as LuaBuilderCache)

        MergeAXMLTask()
                .initBuilderOut(iBuilderOut!!)
                .initActivity(activity!!).doAction(iBuilderCache as LuaBuilderCache)


        CompileLuaTask()
                .initBuilderOut(iBuilderOut!!)
                .initActivity(activity!!).doAction(iBuilderCache as LuaBuilderCache)


        CreateApkTask()
                .initBuilderOut(iBuilderOut!!)
                .initActivity(activity!!).doAction(iBuilderCache as LuaBuilderCache)
    }

    override fun start() {
        GlobalScope.launch {
            try {
                run()
            } catch (it:Exception) {
                e(it.toString())
            } finally {
                iBuilderOut?.end()
            }

        }

    }

    override fun initBuilderOut(builderOut: IBuilderOut): LuaBuilder {
        iBuilderOut = builderOut
        return this
    }

    override fun initProjectInfo(projectInfo: ProjectInfo): LuaBuilder {
        this.projectInfo=projectInfo
        return this
    }

}