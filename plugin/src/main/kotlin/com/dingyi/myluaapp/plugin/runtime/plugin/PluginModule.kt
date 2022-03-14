package com.dingyi.myluaapp.plugin.runtime.plugin

import android.content.Context
import android.content.res.AssetManager

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.PluginModule
import com.dingyi.myluaapp.plugin.api.Properties
import com.dingyi.myluaapp.plugin.runtime.editor.EditorService


import com.dingyi.myluaapp.plugin.runtime.action.ActionService


import com.dingyi.myluaapp.plugin.runtime.project.ProjectService

object PluginModule: PluginModule {


    private var pluginManager: PluginManager? = PluginManager(this)

    private val defaultProjectService = ProjectService(this)

    private val defaultActionService = ActionService(this)

    private val defaultEditorService = EditorService(this)

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

    override suspend fun installPlugin(pluginPath: String) {
        pluginManager?.installPlugin(pluginPath)
    }

    override suspend fun uninstallPlugin(pluginId: String) {
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

    override fun getAssetManager(plugin: Plugin): AssetManager {
        val path = pluginManager?.getPluginPath(plugin)
        if (path == "null") {
            return getAndroidContext()
                .assets
        }
        return getAndroidContext()
            .assets
    }


    override fun getProperties():Properties {
        TODO("Not yet implemented")
    }


    override fun getAndroidContext(): Context {
        return com.dingyi.myluaapp.MainApplication.instance
    }
}