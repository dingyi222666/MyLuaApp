package com.dingyi.myluaapp.build.api

interface Cache {

    fun <T> getCache(key:String):T

    fun putCache(key: String,value:Any)

}