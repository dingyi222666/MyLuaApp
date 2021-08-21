package com.dingyi.editor

import io.github.rosemoe.editor.struct.CompletionItem

/**
 * @author: dingyi
 * @date: 2021/8/22 2:50
 * @description:
 **/
data class ColorCompletionItem(
    var colorLabel:CharSequence,
) : CompletionItem("", "")