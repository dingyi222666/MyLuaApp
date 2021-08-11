package com.dingyi.myluaapp.bean

/**
 * @author: dingyi
 * @date: 2021/8/6 14:54
 * @description:
 **/
data class NewProjectInfo(
    var appName: String,
    var appPackageName: String,
    var template: ProjectTemplates.Template?,
    var useAndroidX: Boolean = false
)
