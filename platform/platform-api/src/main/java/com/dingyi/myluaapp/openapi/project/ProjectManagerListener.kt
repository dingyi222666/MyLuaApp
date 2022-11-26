package com.dingyi.myluaapp.openapi.project

import java.util.EventListener


/**
 * Listener for Project.
 */
interface ProjectManagerListener : EventListener {
    /**
     * Invoked on project open. Executed in EDT.
     *
     * @param project opening project
     */
    fun projectOpened(project: Project) {}


    /**
     * Invoked on project close.
     *
     * @param project closing project
     */
    fun projectClosed(project: Project) {}

    /**
     * Invoked on project close before any closing activities
     */
    fun projectClosing(project: Project) {}
    fun projectClosingBeforeSave(project: Project) {}

    companion object {
        val EMPTY_ARRAY = arrayOfNulls<ProjectManagerListener>(0)
    }
}