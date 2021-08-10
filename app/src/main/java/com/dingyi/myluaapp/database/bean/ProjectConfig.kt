package com.dingyi.myluaapp.database.bean

import org.litepal.crud.LitePalSupport
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
class ProjectConfig: LitePalSupport() {
    var id by Delegates.notNull<Int>()
    var openFiles:MutableList<CodeFile> =mutableListOf()
    lateinit var path:String

    override fun toString(): String {
        return "ProjectConfig(openFiles=$openFiles, path='$path')"
    }


}
