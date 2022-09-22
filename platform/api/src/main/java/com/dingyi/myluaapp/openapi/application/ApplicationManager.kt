package com.dingyi.myluaapp.openapi.application

import android.app.Application

object ApplicationManager {

    fun setAndroidApplication(androidApplication: Application) {
        ourAndroidApplication = androidApplication
    }

    fun getAndroidApplication(): Application {
        return checkNotNull(ourAndroidApplication) { "The android application is not init" }
    }

    fun setIDEApplication(ideApplication: IDEApplication) {
        ourIDEApplication = ideApplication
    }

    fun getIDEApplication(): IDEApplication {
        return checkNotNull(ourIDEApplication) { "The ide application is not init" }
    }


    private var ourAndroidApplication: Application? = null
    private var ourIDEApplication: IDEApplication? = null

}