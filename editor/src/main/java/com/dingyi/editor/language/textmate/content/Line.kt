package com.dingyi.editor.language.textmate.content

import org.eclipse.tm4e.core.model.TMState

/**
 * @author: dingyi
 * @date: 2021/10/10 23:23
 * @description:
 **/
data class Line(
    var line:Int,
    var content:String,
)