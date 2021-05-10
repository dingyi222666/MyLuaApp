package com.dingyi.MyLuaApp.network.callback

import okhttp3.Response

interface HttpCallBack {
    fun callback(response: Response?, exception: Exception?)
}