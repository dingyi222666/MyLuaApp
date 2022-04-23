package com.dingyi.myluaapp.openapi.application

import android.app.Application

object ApplicationManager {

    fun setAndroidApplication(androidApplication: Application) {
        ApplicationManager.outAndroidApplication = androidApplication
    }

    fun getAndroidApplication(): Application {

        return checkNotNull(ApplicationManager.outAndroidApplication) { "The android application is not init" }
    }

    fun setIDEApplication(ideApplication: IDEApplication) {
        ApplicationManager.outIDEApplication = ideApplication
    }

    fun getIDEApplication(): IDEApplication {
        return checkNotNull(ApplicationManager.outIDEApplication) { "The ide application is not init" }
    }


    private var outAndroidApplication: Application? = null
    private var outIDEApplication: IDEApplication? = null

}