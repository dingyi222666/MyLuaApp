package com.dingyi.myluaapp.ide.ui.android.bundle

import android.content.Context
import com.dingyi.myluaapp.openapi.application.ApplicationManager


class AndroidBundle(
    private val targetContext: Context
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
                    CORE_BUNDER = AndroidBundle(ApplicationManager.getAndroidApplication())
                }
                return CORE_BUNDER
            }

    }

}