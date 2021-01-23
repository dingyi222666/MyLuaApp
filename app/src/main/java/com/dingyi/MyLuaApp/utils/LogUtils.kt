package com.dingyi.MyLuaApp.utils

import android.util.Log

fun d(string: String) {
    if (com.dingyi.MyLuaApp.BuildConfig.DEBUG)
        Log.d("dingyi", string)

}

fun e(string: String) {
    if (com.dingyi.MyLuaApp.BuildConfig.DEBUG)
        Log.e("dingyi", string)
}

fun d(vararg string: String) {
    d(string.contentToString())
}

fun e(vararg string: String) {
    e(string.contentToString())
}

