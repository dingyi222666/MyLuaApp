package com.dingyi.myluaapp.builder.api.internal

import com.dingyi.myluaapp.builder.api.Project
import com.dingyi.myluaapp.builder.api.dependency.Dependency
import com.dingyi.myluaapp.builder.api.internal.file.FileResolver




interface ProjectInternal:Project {
    fun addChildProject(childProject: ProjectInternal)

    override fun getBuilder(): BuilderInternal

    fun getFileResolver(): FileResolver

    fun setRootProject(projectInternal: ProjectInternal)


    fun addDependency(dependency: Dependency)

}
