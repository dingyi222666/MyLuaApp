package com.dingyi.myluaapp.build.api.internal

import com.dingyi.myluaapp.build.api.BuildTool
import com.dingyi.myluaapp.build.api.ProjectEvaluationListener
import com.dingyi.myluaapp.build.api.internal.plugins.PluginAwareInternal

interface BuildToolInternal: BuildTool,PluginAwareInternal {


    /**
     * Returns the default project. This is used to resolve relative names and paths provided on the UI.
     */
    fun getDefaultProject(): ProjectInternal

    /**
     * Returns the broadcaster for [ProjectEvaluationListener] events for this build
     */
    fun getProjectEvaluationBroadcaster(): ProjectEvaluationListener
}