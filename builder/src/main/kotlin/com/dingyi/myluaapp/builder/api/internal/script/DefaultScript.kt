package com.dingyi.myluaapp.builder.api.internal.script

import com.dingyi.myluaapp.builder.api.Project
import com.dingyi.myluaapp.builder.api.Script
import com.dingyi.myluaapp.builder.api.internal.ProjectInternal

class DefaultScript(scriptPath:String):ScriptInternal {
    override fun apply(project: ProjectInternal) {

    }

    override fun getType(): String {
        return "default"
    }
}