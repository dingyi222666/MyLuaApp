package com.dingyi.myluaapp.openapi.application

import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import com.dingyi.myluaapp.util.messages.MessageBus
import kotlinx.coroutines.CoroutineScope


interface IDEApplication : ServiceRegistry {
    fun getApplicationCoroutineScope(): CoroutineScope

    /**
     * Saves all open documents, settings of all open projects, and application settings.
     *
     * @see .saveSettings
     */
    fun saveAll()

    /**
     * Saves application settings. Note that settings for non-roamable components aren't saved by default if they were saved less than
     * 5 minutes ago, see [useSaveThreshold][com.intellij.openapi.components.Storage.useSaveThreshold] for details.
     */
    fun saveSettings()


    /**
     * Exits the application, showing the exit confirmation prompt if it is enabled.
     */
    fun exit()


    fun getMessageBus():MessageBus


}