package com.dingyi.myluaapp.plugin.api

interface Properties {

    fun putString(key:String,value:String)

    fun getString(key: String,value: String): String?

}