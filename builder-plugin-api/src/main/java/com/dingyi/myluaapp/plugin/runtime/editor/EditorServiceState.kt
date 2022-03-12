package com.dingyi.myluaapp.plugin.runtime.editor

import java.nio.file.Path

data class EditorServiceState(
    var lastOpenPath: String?,
    val editors:MutableList<EditorState>
)
