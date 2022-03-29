package com.dingyi.myluaapp.editor.lsp

data class LSPProject(
    val javaSourceList:List<String>,
    val luaSourceList:List<String>,
    val javaLibraryList:List<String>
)
