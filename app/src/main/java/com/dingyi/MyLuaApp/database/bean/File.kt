package com.dingyi.MyLuaApp.database.bean

import org.litepal.crud.LitePalSupport

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
data class File(
    var id:Int,
    var code:String,
    var project: Project,
    var selection:Int
): LitePalSupport()
