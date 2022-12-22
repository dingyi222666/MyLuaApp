package com.dingyi.myluaapp.ide.ui.android.bundle

import android.app.Application
import android.content.Context


class AndroidBundle(
    private val targetContext: Application
) {

    fun message(
        key: Int,
        vararg params: Any?
    ): String {
        return targetContext.getString(key, params)
    }

    companion object {

        private lateinit var CORE_BUNDER: AndroidBundle


        val coreBundle: AndroidBundle
            @Synchronized
            get() {
                if (!this::CORE_BUNDER.isInitialized) {
                    val applicationManagerClass =
                        javaClass.classLoader.loadClass("com.dingyi.myluapp.openapi.application.ApplicationManager")
                    CORE_BUNDER = AndroidBundle(
                        applicationManagerClass.getMethod(
                            "getAndroidApplication",
                            null
                        ).invoke(null) as Application
                    )
                }
                return CORE_BUNDER
            }

    }

}