@file:JvmName("HttpClient")

package com.dingyi.MyLuaApp.network.client

import com.dingyi.MyLuaApp.network.callback.HttpCallBack
import okhttp3.*
import okio.IOException
import java.lang.Exception


fun get(url: String, callback: HttpCallBack) {
    val client = OkHttpClient()
    val builder = Request.Builder()
            .get()
            .url(url)
            .build()
    val call = client.newCall(builder)
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback.callback(null, e)
        }

        override fun onResponse(call: Call, response: Response) {
            callback.callback(response, null)
        }
    })
}

