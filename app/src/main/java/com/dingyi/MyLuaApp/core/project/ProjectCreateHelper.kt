package com.dingyi.MyLuaApp.core.project

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.core.task.SimpleAsyncTask

/** 创建项目的帮助类，帮助创建工程
 *
 */
class ProjectCreateHelper(private val activity: BaseActivity<*>) {
    private var mProjectType=0
    private var mProjectPath=""
    private var mProjectName=""
    private var mProjectPackageName=""


    fun setProjectName(name: String):ProjectCreateHelper {
        this.mProjectName = name
        return this;
    }

    fun setProjectPackageName(name: String):ProjectCreateHelper {
        this.mProjectPackageName = name
        return this;
    }

    fun setProjectPath(name: String):ProjectCreateHelper {
        this.mProjectPath = name
        return this;
    }

    fun setProjectType(name: Int):ProjectCreateHelper {
        this.mProjectType = name
        return this;
    }

    fun execute(callback: Runnable) {
        SimpleAsyncTask.postTask({
            createProject(activity,mProjectType,mProjectPath,mProjectName,mProjectPackageName)
        },callback)
    }

}