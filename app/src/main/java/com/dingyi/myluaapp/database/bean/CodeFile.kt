package com.dingyi.myluaapp.database.bean

import org.litepal.crud.LitePalSupport

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
 class CodeFile: LitePalSupport() {

    var id: Int = 0
    lateinit var projectConfig: ProjectConfig
    var selection: Int = 0
    lateinit var path: String

    override fun toString(): String {
        return "File(id=$id,  selection=$selection, path='$path')"
    }

}
