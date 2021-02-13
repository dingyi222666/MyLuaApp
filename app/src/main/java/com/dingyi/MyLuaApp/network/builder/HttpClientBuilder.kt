package com.dingyi.MyLuaApp.network.builder

import com.dingyi.MyLuaApp.network.callback.HttpCallBack
import com.dingyi.MyLuaApp.network.client.get

class HttpClientBuilder {

    private var url: String?=null;
    private var mode="GET"

    fun get():HttpClientBuilder {
        mode="GET"
        return this;
    }

    fun url(url: String):HttpClientBuilder {
        this.url=url
        return this;
    }

    fun enqueue(callback: HttpCallBack) {
        if (mode=="GET") {
            url?.let { get(it,callback) }
        }
    }

}