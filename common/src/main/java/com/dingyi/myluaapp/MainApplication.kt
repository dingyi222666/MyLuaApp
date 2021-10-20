package com.dingyi.myluaapp

import android.app.Application
import com.dingyi.builder.common.handler.RequestHandler

import com.hjq.http.EasyConfig
import okhttp3.OkHttpClient
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/4 13:02
 * @description:
 **/
class MainApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        instance = this


        //init EasyHttp

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .build()



        EasyConfig.with(okHttpClient) // 是否打印日志
            .setServer("https://www.baidu.com")// 设置服务器配置
            .setLogEnabled(true)
            .setHandler(com.dingyi.builder.common.handler.RequestHandler(this))
            .setRetryCount(2) // 添加全局请求参数
            // 启用配置
            .into()


        LitePal.initialize(this)


    }

    companion object {
        var instance by Delegates.notNull<MainApplication>()
    }

}