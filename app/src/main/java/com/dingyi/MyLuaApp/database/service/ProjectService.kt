package com.dingyi.MyLuaApp.database.service

import com.dingyi.MyLuaApp.database.bean.Project
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll

/**
 * @author: dingyi
 * @date: 2021/8/8 18:33
 * @description:
 **/
object ProjectService {

    fun getProject(path: String): Project {
        return LitePal.where("path = ?", path).find<Project>()
            .run {
                if (size > 0) {
                    get(0)
                } else {
                    Project(
                        LitePal.findAll<Project>().size + 1,
                        mutableListOf(),
                        path
                    ).apply { save() }
                }
            }
    }

}