package com.dingyi.editor.language.textmate.bean
import androidx.annotation.Keep


import com.google.gson.annotations.SerializedName


/**
 * @author: dingyi
 * @date: 2021/10/11 3:48
 * @description:
 **/
@Keep
data class VSCodeThemeBean(
    @SerializedName("name")
    var themeName: String, // Light+ (default light)
    @SerializedName("tokenColors")
    var tokenColors: List<TokenColor>,
    @SerializedName("colors")
    var editorColors: Map<String,String>?
) {

    @Keep
    data class TokenColor(
        @SerializedName("name")
        var name: String, // Function declarations
        @SerializedName("scope")
        var scope: Any, // null
        @SerializedName("settings")
        var style: FontStyle
    ) {

    }
}