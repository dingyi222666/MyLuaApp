package com.dingyi.myluaapp.core.plugin.runtime.plugin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.core.plugin.api.Plugin
import com.dingyi.myluaapp.core.plugin.api.PluginModule
import com.dingyi.myluaapp.core.plugin.api.action.ActionService
import com.dingyi.myluaapp.core.plugin.api.build.BuildService
import com.dingyi.myluaapp.core.plugin.api.editor.EditorService
import com.dingyi.myluaapp.core.plugin.api.project.ProjectService
import com.dingyi.myluaapp.core.plugin.api.ui.UiService

class PluginModule:PluginModule {

    private var currentActivity:AppCompatActivity? = null

    private var pluginManager:PluginManager? = PluginManager(this)

    override fun init(activity: AppCompatActivity) {
       currentActivity = activity
        pluginManager?.init()
    }

    override fun loadPlugin(pluginId: String) {
        pluginManager?.loadPlugin(pluginId)
    }

    override fun loadAllPlugin() {
        pluginManager?.loadAllPlugin()
    }

    override fun installPlugin(pluginPath: String) {
        pluginManager?.installPlugin(pluginPath)
    }

    override fun uninstallPlugin(pluginId: String) {
        pluginManager?.uninstallPlugin(pluginId)
    }

    override fun getAllPlugin(): List<Plugin> {
        return pluginManager?.getAllPlugin() ?: listOf()
    }

    override fun stop() {
        currentActivity = null
        pluginManager = null
    }

    override fun getProjectService(): ProjectService {
        TODO("Not yet implemented")
    }

    override fun getEditorService(): EditorService {
        TODO("Not yet implemented")
    }

    override fun getActionService(): ActionService {
        TODO("Not yet implemented")
    }

    override fun getBuildService(): BuildService {
        TODO("Not yet implemented")
    }

    override fun getResourceManager(plugin: Plugin) {
        TODO("Not yet implemented")
    }

    override fun getProperties() {
        TODO("Not yet implemented")
    }

    override fun getUiService(): UiService {
        TODO("Not yet implemented")
    }

    override fun getAndroidContext(): Context {
        return MainApplication.instance
    }
}