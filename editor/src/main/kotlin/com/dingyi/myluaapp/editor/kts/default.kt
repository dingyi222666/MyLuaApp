package com.dingyi.myluaapp.editor.kts

import android.content.res.Resources


/**
 * @author: dingyi
 * @date: 2021/8/15 7:42
 * @description:
 **/


inline val Int.dp: Int
    get() = (Resources.getSystem().displayMetrics.density * this + 0.5f).toInt()