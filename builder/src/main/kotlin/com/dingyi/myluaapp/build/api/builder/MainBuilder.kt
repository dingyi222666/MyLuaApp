package com.dingyi.myluaapp.build.api.builder

import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.service.ServiceRepository

interface MainBuilder {

    fun getLogger(): ILogger

    fun getProject(): Project

    fun build(command: String)

    fun getServiceRepository(): ServiceRepository

    fun stop()

    fun getMavenRepository():MavenRepository

}