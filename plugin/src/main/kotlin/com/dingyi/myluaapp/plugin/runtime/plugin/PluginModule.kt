package com.dingyi.myluaapp.plugin.runtime.plugin

import android.content.Context
import android.content.res.AssetManager

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.PluginModule
import com.dingyi.myluaapp.plugin.api.Properties
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.runtime.editor.EditorService


import com.dingyi.myluaapp.plugin.runtime.action.ActionService


import com.dingyi.myluaapp.plugin.runtime.project.ProjectService
import com.dingyi.myluaapp.plugin.runtime.ui.UiService
import java.io.File

object PluginModule: PluginModule {


    private var pluginManager: PluginManager? = PluginManager(this)

    private val defaultProjectService = ProjectService(this)

    private val defaultActionService = ActionService(this)

    private val defaultEditorService = EditorService(this)

    private val defaultUiService = UiService(this)

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

    override suspend fun installPlugin(pluginPath: String):Int {
        return pluginManager?.installPlugin(pluginPath) ?: -1
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

    override fun getUiService():UiService {
        return defaultUiService
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

    override fun <T> getBuildService() = defaultBuildService as BuildService<T>


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
        return MainApplication.instance
    }

    override fun getPluginDir(): File {
        return File(Paths.pluginDir)
    }

    override val apiVersion: Int
        get() = 2
}