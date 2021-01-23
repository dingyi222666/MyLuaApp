package com.dingyi.MyLuaApp.utils

import android.app.Activity
import okhttp3.*
import java.io.IOException


fun get(activity: Activity, url: String, callback: CallBack) {
    val client = OkHttpClient()
    val builder = Request.Builder()
            .get()
            .url(url)
            .build()
    val call = client.newCall(builder)
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            activity.runOnUiThread {
                callback.fail()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            activity.runOnUiThread {
                callback.successful(response)
            }
        }
    })
}


interface CallBack {
    fun fail()
    fun successful(result: Response)
}