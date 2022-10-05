package com.dingyi.myluaapp.openapi.application

import android.app.Application

object ApplicationManager {

    fun setAndroidApplication(androidApplication: Application) {
        ourAndroidApplication = androidApplication
    }

    fun getAndroidApplication(): Application {
        return checkNotNull(ourAndroidApplication) { "The android application is not init" }
    }

    fun setApplication(ideApplication: IDEApplication) {
        ourApplication = ideApplication
    }

    fun getApplication(): IDEApplication {
        return checkNotNull(ourApplication) { "The ide application is not init" }
    }


    private var ourAndroidApplication: Application? = null
    private var ourApplication: IDEApplication? = null

}