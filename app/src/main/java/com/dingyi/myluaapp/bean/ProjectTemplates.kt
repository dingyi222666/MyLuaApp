package com.dingyi.myluaapp.bean


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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