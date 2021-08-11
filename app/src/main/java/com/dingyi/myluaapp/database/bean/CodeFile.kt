package com.dingyi.myluaapp.database.bean

import org.litepal.crud.LitePalSupport

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
class CodeFile : LitePalSupport() {

    var id: Int = 0
    lateinit var projectConfig: ProjectConfig
    var openSelection: Int = 0
    lateinit var filePath: String

    override fun toString(): String {
        return "File(id=$id,  selection=$openSelection, path='$filePath')"
    }

}
