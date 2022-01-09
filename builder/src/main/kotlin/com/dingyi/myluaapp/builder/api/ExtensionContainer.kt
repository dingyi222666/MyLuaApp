package com.dingyi.myluaapp.builder.api

interface ExtensionContainer {

    fun <T> create(clazz:Class<T>) : T

    fun <T> findByName(name:String): Any

    fun <T> findByType(type:Class<T>):T

}