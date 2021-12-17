package com.dingyi.myluaapp

import android.app.Application
import android.content.Context
import org.litepal.LitePal
import kotlin.properties.Delegates
import com.hjq.language.MultiLanguages




/**
 * @author: dingyi
 * @date: 2021/8/4 13:02
 * @description:
 **/
class MainApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        instance = this


        LitePal.initialize(this)

        // 初始化语种切换框架
        MultiLanguages.init(this);

    }

    override fun attachBaseContext(base: Context) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base))
    }

    companion object {
        var instance by Delegates.notNull<MainApplication>()
    }

}