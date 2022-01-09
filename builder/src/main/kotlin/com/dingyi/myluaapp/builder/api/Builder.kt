package com.dingyi.myluaapp.builder.api

import com.dingyi.myluaapp.builder.api.task.TaskList

import java.io.File


interface Builder {

    val builderVersion: String

    val builderHomeDir: File


    val parent: Builder?


    val rootProject: Project

    val taskList: TaskList


    fun logWarn(tag: String, message: String)

    fun logInfo(tag: String, message: String)

    fun logError(tag: String, message: String)

    fun logDebug(tag: String, message: String)

    fun logVerbose(tag: String, message: String)

    val gradle: Builder
}