package com.dingyi.MyLuaApp.database.bean

import org.litepal.crud.LitePalSupport

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
data class Project(
    var id:Int,
    var openFiles:List<File>,
    var path:String
): LitePalSupport()
