package com.dingyi.myluaapp.openapi.application

import android.app.Application

object ApplicationManager {

    fun setAndroidApplication(androidApplication: Application) {
        ApplicationManager.ourAndroidApplication = androidApplication
    }

    fun getAndroidApplication(): Application {
        return checkNotNull(ApplicationManager.ourAndroidApplication) { "The android application is not init" }
    }

    fun setIDEApplication(ideApplication: IDEApplication) {
        ApplicationManager.ourIDEApplication = ideApplication
    }

    fun getIDEApplication(): IDEApplication {
        return checkNotNull(ApplicationManager.ourIDEApplication) { "The ide application is not init" }
    }


    private var ourAndroidApplication: Application? = null
    private var ourIDEApplication: IDEApplication? = null

}