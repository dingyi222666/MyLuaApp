@file:JvmName("TextUtils")

package com.dingyi.MyLuaApp.utils


import android.util.Log

const val TAG = "showDebug"

fun printDebug(text: String) {
    Log.d(TAG, text)
}

fun printDebug(vararg text: Any) {
    val builder = StringBuilder()
    text.forEach {
        builder.append(it.toString()).append(" ")
    }
    Log.d(TAG, builder.toString())
}

fun printDebug(exception: Throwable) {
    printDebug(exception.toString())
}

fun printError(text: String) {
    Log.e(TAG, text)
}

fun printError(exception: Throwable) {
    printError(exception.toString())
}


