package com.dingyi.myluaapp.core.plugin.api.context

import android.content.Context
import com.dingyi.myluaapp.core.plugin.api.Plugin
import com.dingyi.myluaapp.core.plugin.api.action.ActionService
import com.dingyi.myluaapp.core.plugin.api.build.BuildService
import com.dingyi.myluaapp.core.plugin.api.editor.EditorService
import com.dingyi.myluaapp.core.plugin.api.project.ProjectService
import com.dingyi.myluaapp.core.plugin.api.ui.UIProvider
import com.dingyi.myluaapp.core.plugin.api.ui.UiService
import java.io.InputStream

interface PluginContext {


    fun getProjectService():ProjectService

    fun getEditorService():EditorService

    fun getActionService():ActionService

    fun getBuildService(): BuildService

    fun getResourceManager(plugin: Plugin)

    fun getProperties()

    fun getUiService():UiService

    fun getAndroidContext():Context

}