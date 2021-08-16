package com.dingyi.editor.language.lua

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.androlua.LuaApplication
import java.lang.reflect.Field


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