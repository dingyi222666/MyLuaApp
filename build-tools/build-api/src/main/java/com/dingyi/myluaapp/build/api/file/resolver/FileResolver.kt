package com.dingyi.myluaapp.build.api.file.resolver

import com.dingyi.myluaapp.build.api.Project

interface FileResolver {

    fun resolve(path:String,project: Project)


}