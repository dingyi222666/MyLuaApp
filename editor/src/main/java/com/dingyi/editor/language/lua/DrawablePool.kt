package com.dingyi.editor.language.lua

import android.graphics.drawable.Drawable
import com.androlua.LuaApplication


/**
 * @author: dingyi
 * @date: 2021/8/15 7:18
 * @description:
 **/
object DrawablePool {
    private val pool = mutableMapOf<Int, Drawable>()

    fun clear() {
        pool.clear()
    }


    fun loadDrawable(resId: Int): Drawable? {
        if (!pool.containsKey(resId)) {
            val drawable = LuaApplication.getInstance().getDrawable(resId)
            drawable?.let { pool.put(resId, it) }
        }
        return pool[resId]
    }

}