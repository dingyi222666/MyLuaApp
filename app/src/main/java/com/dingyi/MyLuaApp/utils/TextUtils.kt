@file:JvmName("TextUtils")
package com.dingyi.MyLuaApp.utils


import android.util.Log
import java.lang.Exception

const val TAG = "showDebug"

fun printDebug(text: String) {
    Log.d(TAG, text)
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


