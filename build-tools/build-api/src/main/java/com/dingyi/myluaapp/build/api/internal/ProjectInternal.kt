package com.dingyi.myluaapp.build.api.internal

import com.dingyi.myluaapp.build.api.Project

interface ProjectInternal: Project {

    override fun getBuildTool():BuildToolInternal
}