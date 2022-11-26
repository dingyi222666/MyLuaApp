@file:JvmName("ApplicationLoader")

package com.dingyi.myluaapp.ide.application

import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.ide.plugins.PluginManagerCore
import com.dingyi.myluaapp.ide.startup.IdeStartup

import java.util.concurrent.CompletionStage


@Suppress("SSBasedInspection")
private val LOG = Logger.getInstance("#com.dingyi.myluaapp.idea.application.ApplicationLoader")

fun initApplication() {
    val loadAndInitPluginFuture = PluginManagerCore.initPlugins(IdeStartup::class.java.classLoader)

}