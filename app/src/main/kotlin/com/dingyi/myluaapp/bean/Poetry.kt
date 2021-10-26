package com.dingyi.myluaapp.bean


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep

data class Poetry(
    val author: String, // 徐有贞
    val category: String, // 古诗文-节日-中秋节
    val content: String, // 好时节，愿得年年，常见中秋月。
    val origin: String // 中秋月·中秋月
)