@file:JvmName("ApplicationLoader")

package com.dingyi.myluaapp.ide.application

import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.ide.ApplicationLoadListener
import com.dingyi.myluaapp.ide.plugins.PluginManagerCore
import com.dingyi.myluaapp.ide.startup.IdeStarter
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.progress.ProcessCanceledException
import java.util.concurrent.CompletionStage
import java.util.concurrent.ForkJoinPool


private val LOG = Logger.getInstance("#com.dingyi.myluaapp.idea.application.ApplicationLoader")

fun initApplication(prepareUiFuture: CompletionStage<*>) {
    val loadAndInitPluginFuture = PluginManagerCore.initPlugins(IdeStarter::class.java.classLoader)


    prepareUiFuture.thenComposeAsync({
        val app = IDEApplicationImpl()
        loadAndInitPluginFuture
            .thenAccept { plugins ->
                /*runActivity("app component registration") {*/
                app.registerComponents(plugins, null)
                /*}*/

                 //startApp(app, IdeStarter(), initAppActivity, plugins, args)

            }
    },  { ForkJoinPool.commonPool().execute(it) })
        .exceptionally {
            it.printStackTrace(System.err)
            //StartupAbortedException.processException(it)
            null
        }

}


internal fun initConfigurationStore(app: IDEApplicationImpl) {
    val configPath = PathManager.getConfigDir()
    for (listener in ApplicationLoadListener.EP_NAME.iterable) {
        try {
            (listener ?: break).beforeApplicationLoaded(app, configPath)
        }
        catch (e: ProcessCanceledException) {
            throw e
        }
        catch (e: Throwable) {
            LOG.error(e)
        }
    }

    // we set it after beforeApplicationLoaded call, because app store can depend on stream provider state
    // app.stateStore.setPath(configPath)

}