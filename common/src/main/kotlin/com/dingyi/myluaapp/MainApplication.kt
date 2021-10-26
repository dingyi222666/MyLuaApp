package com.dingyi.myluaapp

import android.app.Application
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



        LitePal.initialize(this)


    }

    companion object {
        var instance by Delegates.notNull<MainApplication>()
    }

}