package com.dingyi.myluaapp.openapi.application

import android.app.Application
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer


object ApplicationManager {

    @JvmStatic
    fun setAndroidApplication(androidApplication: Application) {
        ourAndroidApplication = androidApplication
    }

    fun getAndroidApplication(): Application {
        return checkNotNull(ourAndroidApplication) { "The android application is not init" }
    }

    fun setApplication(ideApplication: IDEApplication) {
        ourApplication = ideApplication
    }

    fun setApplication(ideApplication: IDEApplication, parent: Disposable) {
        val old = ourApplication
        Disposer.register(parent) {
            if (old != null) { // to prevent NPEs in threads still running
                setApplication(old)
            }
        }
        setApplication(ideApplication)
    }

    fun getApplication(): IDEApplication {
        return checkNotNull(ourApplication) { "The ide application is not init" }
    }


    private var ourAndroidApplication: Application? = null
    private var ourApplication: IDEApplication? = null

}