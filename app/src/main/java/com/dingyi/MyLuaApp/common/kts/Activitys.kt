package com.dingyi.MyLuaApp.common.kts

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @author: dingyi
 * @date: 2021/8/4 14:03
 * @description:
 **/


inline val Activity.versionCode: Int
    get() = packageManager.getPackageInfo(packageName, 0).versionCode


inline fun <reified T> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

fun Context.getStringArray(resId: Int):Array<String> {
    return this.resources.getStringArray(resId)
}

fun Context.getAttributeColor(resId: Int): Int {
    val typedArray = obtainStyledAttributes(intArrayOf(resId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}
