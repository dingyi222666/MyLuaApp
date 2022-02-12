package com.dingyi.myluaapp.plugin.api.context

import android.content.Context
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.action.ActionService
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.api.editor.EditorService
import com.dingyi.myluaapp.plugin.api.project.ProjectService

interface PluginContext {


    fun getProjectService(): ProjectService

    fun getEditorService(): EditorService

    fun getActionService(): ActionService

    fun getBuildService(): BuildService

    fun getResourceManager(plugin: Plugin)

    fun getProperties()


    fun getAndroidContext():Context

}