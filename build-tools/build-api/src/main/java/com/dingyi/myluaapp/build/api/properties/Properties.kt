package com.dingyi.myluaapp.build.api.properties

interface Properties {

    fun getProperty(key: String): Any

    fun setProperty(key: String, value: Any)


    fun findProperty(key: String): Any?
}