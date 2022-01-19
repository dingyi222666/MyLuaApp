package com.dingyi.myluaapp.builder.api.builder

import com.dingyi.myluaapp.builder.api.project.Project
import com.dingyi.myluaapp.builder.api.logger.ILogger

interface MainBuilder {

    fun getLogger():ILogger

    fun getProject(): Project

    fun build(command:String)

}