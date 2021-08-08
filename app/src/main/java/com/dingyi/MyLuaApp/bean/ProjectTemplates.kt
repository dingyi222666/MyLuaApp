package com.dingyi.MyLuaApp.bean


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProjectTemplates(
    @SerializedName("templates")
    val templates: List<Template>
) {
    @Keep
    data class Template(
        @SerializedName("name")
        val name: String, // 空模板
        @SerializedName("path")
        val path: String, // zip/emptyProject.zip
        @SerializedName("replaces")
        val replaces: List<String>
    )
}