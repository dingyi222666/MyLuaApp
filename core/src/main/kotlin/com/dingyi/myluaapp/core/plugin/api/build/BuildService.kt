package com.dingyi.myluaapp.core.plugin.api.build

interface BuildService {

    fun addBuildService(className: String)

    fun getAllBuildService():List<String>

}