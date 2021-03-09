package com.dingyi.MyLuaApp.core.project

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtil

class ProjectManager(activity: BaseActivity<*>, private val info: ProjectInfo) {

    private val sharedPreferencesUtil=SharedPreferencesUtil("editor",activity);

    fun getLastOpenPath(): String? {
        return sharedPreferencesUtil.get(info.path, getDefaultPath(info.path));
    }

    fun putOpenPath(path: String) {
        sharedPreferencesUtil.put(info.path, path);
    }

    fun getShortPath(path:String):String {
        return path.substring(info.path.length+1)
    }

}