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
    var openSelectionLine = 0
    var openSelectionColumn = 0
    var code = ""
    lateinit var filePath: String


    override fun toString(): String {
        return "CodeFile(id=$id, openSelectionLine=$openSelectionLine, openSelectionColumn=$openSelectionColumn, code='$code', filePath='$filePath')"
    }


}
