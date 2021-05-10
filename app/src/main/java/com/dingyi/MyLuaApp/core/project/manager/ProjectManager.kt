package com.dingyi.MyLuaApp.core.project.manager

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.core.project.getDefaultPath
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtil

class ProjectManager(activity: BaseActivity<*>, private val info: ProjectInfo) {

    private val mSharedPreferencesUtil = SharedPreferencesUtil(activity, "editor")

    fun getLastOpenPath(): String? {
        return mSharedPreferencesUtil.get(info.projectPath, getDefaultPath(info.projectPath))
    }

    fun putOpenPath(path: String) {
        mSharedPreferencesUtil.put(info.projectPath, path)
    }

    fun getShortPath(path: String): String {
        return path.substring(info.projectPath.length + 1)
    }

    fun getAllPath(path: String): String {
        return info.projectPath + '/' + path
    }


}