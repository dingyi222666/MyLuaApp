package com.dingyi.myluaapp.builder.api.file

import com.dingyi.myluaapp.builder.api.Project

interface FileTree {


    fun getFiles(project: Project): List<String>

}