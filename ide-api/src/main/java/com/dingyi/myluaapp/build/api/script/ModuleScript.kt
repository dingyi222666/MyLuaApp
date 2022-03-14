package com.dingyi.myluaapp.build.api.script

interface ModuleScript {
    fun getMainBuilderScript(): Script

    fun getAllScript(): List<Script>


}