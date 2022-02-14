package com.dingyi.myluaapp.plugin.runtime.editor

import com.dingyi.myluaapp.plugin.api.editor.EditorState

data class EditorState(
    var path:String,
    var line:Int,
    var column:Int,
    var scrollX:Float,
    var scrollY:Float,
    var textSize:Float,
):EditorState
