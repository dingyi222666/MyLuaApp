package com.dingyi.myluaapp.plugin.runtime.plugin

import android.content.Context

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.build.BuildMain
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.PluginModule
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.runtime.editor.EditorService


import com.dingyi.myluaapp.plugin.runtime.action.ActionService


import com.dingyi.myluaapp.plugin.runtime.project.ProjectService

object PluginModule: PluginModule {


    private var pluginManager: PluginManager? = PluginManager(this)

    private val defaultProjectService = ProjectService(this)

    private val defaultActionService = ActionService(this)

    private val defaultEditorService = EditorService()

    private val defaultBuildService =
        com.dingyi.myluaapp.plugin.runtime.build.BuildService()


    override fun init() {
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
        defaultBuildService.stop()
        pluginManager?.stop()
        pluginManager = null
    }

    override fun getProjectService(): ProjectService {
        return defaultProjectService
    }

    override fun getEditorService(): EditorService {
        return defaultEditorService
    }

    override fun getActionService(): ActionService {
        return defaultActionService
    }

    override fun getBuildService(): com.dingyi.myluaapp.plugin.runtime.build.BuildService {
        return defaultBuildService
    }

    override fun getResourceManager(plugin: Plugin) {
        TODO("Not yet implemented")
    }

    override fun getProperties() {
        TODO("Not yet implemented")
    }


    override fun getAndroidContext(): Context {
        return MainApplication.instance
    }
}