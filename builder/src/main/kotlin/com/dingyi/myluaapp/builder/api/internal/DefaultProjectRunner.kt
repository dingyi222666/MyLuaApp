package com.dingyi.myluaapp.builder.api.internal

class DefaultProjectRunner(private val builderInternal: BuilderInternal) : ProjectRunner {

    override fun getBuilderInternal(): BuilderInternal {
        return builderInternal
    }

    override fun runAllProject(group: String) {
        TODO("Not yet implemented")
    }

    override fun run(group: String) {
        TODO("Not yet implemented")
    }

    override fun runProject(projectInternal: ProjectInternal, group: String) {
        TODO("Not yet implemented")
    }



}
