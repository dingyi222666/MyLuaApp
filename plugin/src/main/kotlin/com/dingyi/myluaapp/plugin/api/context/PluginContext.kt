package com.dingyi.myluaapp.plugin.api.context

import android.content.Context
import android.content.res.AssetManager
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.Properties
import com.dingyi.myluaapp.plugin.api.action.ActionService
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.api.editor.EditorService
import com.dingyi.myluaapp.plugin.api.project.ProjectService

interface PluginContext {


    fun getProjectService(): ProjectService

    fun getEditorService(): EditorService

    fun getActionService(): ActionService

    fun getBuildService(): BuildService<*>

    fun getAssetManager(plugin: Plugin):AssetManager

    fun getProperties(): Properties


    /**
     * Return the base context based on the plugin, the context can only be used to get assets and resource resources, not to used create view
     */
    fun getAndroidContext():Context

}