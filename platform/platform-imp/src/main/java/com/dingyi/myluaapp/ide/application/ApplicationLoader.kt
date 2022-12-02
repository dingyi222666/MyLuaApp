@file:JvmName("ApplicationLoader")

package com.dingyi.myluaapp.ide.application

import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.ide.plugins.PluginManagerCore
import com.dingyi.myluaapp.ide.startup.IdeStartup
import com.intellij.util.ui.EDT

import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool


@Suppress("SSBasedInspection")
private val LOG = Logger.getInstance("#com.dingyi.myluaapp.idea.application.ApplicationLoader")

fun initApplication(prepareUiFuture: CompletionStage<*>) {
    val loadAndInitPluginFuture = PluginManagerCore.initPlugins(IdeStartup::class.java.classLoader)


    prepareUiFuture.thenComposeAsync({
        val app = IDEApplicationImpl()
        loadAndInitPluginFuture
            .thenAccept { plugins ->
                /*runActivity("app component registration") {*/
                app.registerComponents(plugins, null)
                /*}*/

                // startApp(app, IdeStarter(), initAppActivity, plugins, args)

            }
    },  { ForkJoinPool.commonPool().execute(it) })
        .exceptionally {
            it.printStackTrace(System.err)
            //StartupAbortedException.processException(it)
            null
        }

}