package com.dingyi.MyLuaApp.common.kts

import android.view.Menu
import androidx.core.view.children

/**
 * @author: dingyi
 * @date: 2021/8/4 17:51
 * @description:
 **/


fun Menu.iconColor(color: Int) {
    this.children.forEach { item ->
        val drawable = item.icon
        drawable?.setTint(color)
    }
}