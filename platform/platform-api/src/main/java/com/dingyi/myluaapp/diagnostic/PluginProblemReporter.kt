package com.dingyi.myluaapp.diagnostic

import com.dingyi.myluaapp.openapi.application.ApplicationManager


internal interface PluginProblemReporter {
    fun createPluginExceptionByClass(
        errorMessage: String,
        cause: Throwable?,
        pluginClass: Class<*>
    ): PluginException

    companion object {
        //if the application isn't initialized yet return silly implementation which reports all plugins problems as platform ones
        val instance: PluginProblemReporter
            get() = ApplicationManager.getApplication().getService(PluginProblemReporter::class.java)
    }
}
