package com.dingyi.editor.language.java.api

import com.androlua.LuaApplication

/**
 * @author: dingyi
 * @date: 2021/8/20 23:15
 * @description:
 **/
object SystemApiHelper {

    fun findClass(className: String): Boolean {
       return runCatching {
           LuaApplication.getInstance().classLoader.loadClass(className)
       }.isSuccess
    }


}