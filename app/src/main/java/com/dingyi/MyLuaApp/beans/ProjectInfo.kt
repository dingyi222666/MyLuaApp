package com.dingyi.MyLuaApp.bean

data class ProjectInfo(val path:String,val type:Int,val name:String,val versionName:String,val versionCode:String,val packageName:String) {


    companion object {
        val LUA_PEOJECT=0xff
        val GRADLE_PROJECT=0x1f
    }
}
