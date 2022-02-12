package com.dingyi.myluaapp.editor.data

import io.github.rosemoe.sora.data.NavigationItem

/**
 * @author: dingyi
 * @date: 2021/9/3 19:52
 * @description:
 **/

/**
 * @param column current column position
 * @param line current line position
 * @param content the item description
 */
data class ColumnNavigationItem(
    val column: Int = 0,
    private val _line: Int,
    private val _label: String,
    val type: String
) : NavigationItem(_line, _label) {

}