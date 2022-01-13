package com.dingyi.myluaapp.builder.api

interface Plugin<T:Project> {
    fun apply(t:T)
    val name:String
    val id:String
}