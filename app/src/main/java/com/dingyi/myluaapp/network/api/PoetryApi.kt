package com.dingyi.myluaapp.network.api

import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestServer

/**
 * @author: dingyi
 * @date: 2021/8/4 15:44
 * @description:
 **/
class PoetryApi: IRequestServer, IRequestApi {
    override fun getHost(): String {
       return "https://v1.jinrishici.com/"
    }

    override fun getApi(): String {
       return "all.json"
    }


}