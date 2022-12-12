package com.dingyi.myluaapp.ide


import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import java.nio.file.Path


interface ApplicationLoadListener {


    fun beforeApplicationLoaded(application: IDEApplication,  configPath: Path)

    companion object {
        val EP_NAME: ExtensionPointName<ApplicationLoadListener> =
            ExtensionPointName("com.intellij.ApplicationLoadListener")
    }
}
