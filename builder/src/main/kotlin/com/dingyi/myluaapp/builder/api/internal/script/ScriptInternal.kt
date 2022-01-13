package com.dingyi.myluaapp.builder.api.internal.script

import com.dingyi.myluaapp.builder.api.Project
import com.dingyi.myluaapp.builder.api.Script
import com.dingyi.myluaapp.builder.api.internal.ProjectInternal

interface ScriptInternal:Script {

    fun apply(project: ProjectInternal)

    override fun apply(project: Project) {
        this.apply(project as ProjectInternal)
    }
}
