package com.dingyi.MyLuaApp.network.callback

import okhttp3.Response
import java.lang.Exception

interface HttpCallBack {
    fun callback(response: Response?, exception: Exception?)
}