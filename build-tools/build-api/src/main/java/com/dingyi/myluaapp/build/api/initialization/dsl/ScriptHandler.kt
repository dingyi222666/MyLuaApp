package com.dingyi.myluaapp.build.api.initialization.dsl

import com.dingyi.myluaapp.build.api.artifacts.dsl.DependencyHandler
import com.dingyi.myluaapp.build.api.artifacts.dsl.RepositoryHandler

interface ScriptHandler {

    fun getDependencyHandler():DependencyHandler

    fun getRepositoryHandler(): RepositoryHandler
}