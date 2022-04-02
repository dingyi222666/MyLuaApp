package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.properties.Properties

interface BuildTool:Properties {

    fun getVersion(): String


}