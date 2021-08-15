package com.dingyi.editor.kts

import com.androlua.LuaApplication

/**
 * @author: dingyi
 * @date: 2021/8/15 7:42
 * @description:
 **/


inline val Int.dp: Int
    get() = (LuaApplication.getInstance().resources.displayMetrics.density * this + 0.5f).toInt()