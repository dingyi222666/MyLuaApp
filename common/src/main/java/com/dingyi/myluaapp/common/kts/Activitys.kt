package com.dingyi.myluaapp.common.kts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


/**
 * @author: dingyi
 * @date: 2021/8/4 14:03
 * @description:
 **/


inline val Activity.versionCode: Int
    get() = packageManager.getPackageInfo(packageName, 0).versionCode


inline fun <reified T> Activity.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun Activity.startActivity(targetClass: Class<*>, block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, targetClass).apply(block))
}

fun AppCompatActivity.openDocument(fileType: String, callback: (Uri) -> Unit) {
    registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        callback(it)
    }.launch(arrayOf(fileType))
}

fun Context.getStringArray(resId: Int): Array<String> {
    return this.resources.getStringArray(resId)
}

fun Context.getAttributeColor(resId: Int): Int {
    val typedArray = obtainStyledAttributes(intArrayOf(resId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun <T> SharedPreferences.edit(block: SharedPreferences.Editor.() -> T): T {
    var result: T
    this.edit().apply {
        result = block(this)
    }.apply()
    return result
}

