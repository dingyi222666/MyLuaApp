package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.plugins.PluginContainer
import com.dingyi.myluaapp.build.api.properties.Properties

interface BuildTool:Properties {

    fun getVersion(): String

    fun getPluginContainer(): PluginContainer


    /**
     * Adds a listener to this build, to receive notifications as projects are evaluated.
     *
     * @param listener The listener to add. Does nothing if this listener has already been added.
     * @return The added listener.
     */
    fun addProjectEvaluationListener(listener: ProjectEvaluationListener): ProjectEvaluationListener

}