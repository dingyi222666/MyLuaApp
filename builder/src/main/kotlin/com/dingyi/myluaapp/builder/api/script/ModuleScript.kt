package com.dingyi.myluaapp.builder.api.script

interface ModuleScript {
    fun getMainBuilderScript():Script

    fun getAllScript():List<Script>


}