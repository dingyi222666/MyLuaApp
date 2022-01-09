package com.dingyi.myluaapp.builder.api

interface Script {
    fun apply(project: Project)
    fun getType(): String
}