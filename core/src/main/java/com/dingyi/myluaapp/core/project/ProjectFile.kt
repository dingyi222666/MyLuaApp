package com.dingyi.myluaapp.core.project

import com.dingyi.myluaapp.common.kts.toFile

/**
 * @author: dingyi
 * @date: 2021/10/20 14:40
 * @description:
 **/
class ProjectFile(path:String) {

    private val file = path.toFile()


    private var change = false


    init {

    }

    fun getFileMD5():String {
     return ""
    }

    /**
     * commit change to database
     */
    fun commitChange(text:CharSequence) {

    }




}