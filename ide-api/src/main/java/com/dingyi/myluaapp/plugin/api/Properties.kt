package com.dingyi.myluaapp.plugin.api

interface Properties {


    //Add put and get Int
    fun putInt(key: String, value: Int)
    fun getInt(key: String): Int?
    //Add put and get String
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    //Add put and get Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    //Add put and get String Array
    fun putStringArray(key: String, value: Set<String>)
    fun getStringArray(key: String): Set<String>?


}