package com.dingyi.myluaapp.plugin.api.build

interface BuildService {

    fun addBuildService(className: String)

    fun getAllBuildService():List<String>

}