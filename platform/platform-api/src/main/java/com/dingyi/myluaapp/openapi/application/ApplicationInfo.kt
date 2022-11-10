package com.dingyi.myluaapp.openapi.application

import com.dingyi.myluaapp.openapi.application.ApplicationManager.getApplication
import com.dingyi.myluaapp.openapi.extensions.PluginId


/**
 * Provides IDE version/help and vendor information.
 */
abstract class ApplicationInfo {

    abstract val build: Int


    /**
     * @return `true` if the specified plugin is an essential part of the IDE, so it cannot be disabled and isn't shown in *Settings | Plugins*.
     */
    abstract fun isEssentialPlugin( pluginId: String): Boolean

    abstract fun isEssentialPlugin( pluginId: PluginId): Boolean
    abstract fun getEssentialPluginsIds(): List<PluginId>

    companion object {
        val instance: ApplicationInfo
            get() = getApplication().get(ApplicationInfo::class.java)

    }
}
