package com.dingyi.editor.data

import io.github.rosemoe.sora.data.CompletionItem

/**
 * @author: dingyi
 * @date: 2021/8/22 2:50
 * @description:
 **/
data class ColorCompletionItem(
    var colorLabel:CharSequence,
) : CompletionItem("", "")