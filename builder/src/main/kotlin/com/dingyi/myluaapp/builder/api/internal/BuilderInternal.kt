package com.dingyi.myluaapp.builder.api.internal

import com.dingyi.myluaapp.builder.api.Builder
import com.dingyi.myluaapp.builder.api.internal.plugin.PluginContainer


interface BuilderInternal: Builder {



    fun getProjectRunner(): ProjectRunner


    fun getDefaultProject(): ProjectInternal


    fun setDefaultProject(defaultProject: ProjectInternal)


    fun setRootProject(rootProject: ProjectInternal)

    fun getProjectContainer():ProjectContainer

    fun getPluginContainer():PluginContainer

}