package com.dingyi.myluaapp.builder.api.dependency

import com.dingyi.myluaapp.builder.api.Project

interface ProjectDependency : Dependency {

    fun getDependencyProject(): Project
}