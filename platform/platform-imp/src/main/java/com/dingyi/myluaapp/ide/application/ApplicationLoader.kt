@file:JvmName("ApplicationLoader")

package com.dingyi.myluaapp.ide.application

import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.diagnostic.logger
import com.dingyi.myluaapp.ide.ApplicationInitializedListener
import com.dingyi.myluaapp.ide.ApplicationLoadListener
import com.dingyi.myluaapp.ide.plugins.PluginDescriptorImpl
import com.dingyi.myluaapp.ide.plugins.PluginManagerCore
import com.dingyi.myluaapp.ide.startup.IdeStarter
import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.application.PathManager
import com.dingyi.myluaapp.openapi.dsl.plugin.service.PreloadMode
import com.dingyi.myluaapp.openapi.dsl.plugin.service.ServiceDslBuilder
import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import com.intellij.openapi.progress.ProcessCanceledException
import java.io.IOException
import java.lang.reflect.Modifier
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ForkJoinTask
import kotlin.io.path.Path
import kotlin.io.path.createDirectories


private val LOG = Logger.getInstance("#com.dingyi.myluaapp.idea.application.ApplicationLoader")

fun initApplication(prepareUiFuture: CompletionStage<*>) {
    val loadAndInitPluginFuture = PluginManagerCore.initPlugins(IdeStarter::class.java.classLoader)


    prepareUiFuture.thenComposeAsync({
        val app = IDEApplicationImpl()
        loadAndInitPluginFuture
            .thenAccept { plugins ->
                app.registerComponents(plugins, null)

                startApp(app, IdeStarter(), plugins)

            }
    }, { ForkJoinPool.commonPool().execute(it) })
        .exceptionally {
            it.printStackTrace(System.err)
            //StartupAbortedException.processException(it)
            null
        }

}


internal fun initConfigurationStore(app: IDEApplicationImpl) {
    val configPath = Path(PathManager.configPath)
    for (listener in ApplicationLoadListener.EP_NAME.iterable) {
        try {
            (listener ?: break).beforeApplicationLoaded(app, configPath)
        } catch (e: ProcessCanceledException) {
            throw e
        } catch (e: Throwable) {
            LOG.error(e)
        }
    }

    // we set it after beforeApplicationLoaded call, because app store can depend on stream provider state
    // app.stateStore.setPath(configPath)

}


internal fun preloadServicesImpl(
    plugins: List<PluginDescriptorImpl>,
    container: IDEApplication,
    activityPrefix: String,
    onlyIfAwait: Boolean
): Pair<CompletableFuture<Void?>, CompletableFuture<Void?>> {
    val asyncPreloadedServices = mutableListOf<ForkJoinTask<*>>()
    val syncPreloadedServices = mutableListOf<ForkJoinTask<*>>()
    val emptyMap = emptyMap<String, ServiceDslBuilder.ServiceImplBuilder>()
    for (plugin in plugins) {
        val pluginServices = plugin.services
        serviceLoop@ for ((interfaceClass, serviceBean) in
        (pluginServices?.applicationLevelServices?.plus(pluginServices.projectLevelServices)
            ?: emptyMap)) {
            val list: MutableList<ForkJoinTask<*>> = when (serviceBean.preload) {
                PreloadMode.TRUE -> {
                    if (onlyIfAwait) {
                        continue@serviceLoop
                    } else {
                        asyncPreloadedServices
                    }
                }

                PreloadMode.AWAIT -> syncPreloadedServices
                PreloadMode.FALSE -> continue@serviceLoop
                else -> throw IllegalStateException("Unknown preload mode ${serviceBean.preload}")
            }


            list.add(ForkJoinTask.adapt task@{
                if (/*isServicePreloadingCancelled ||*/ container.isDisposed) {
                    return@task
                }

                try {
                    /* val instance = */
                    container.instantiateClass(
                        plugin.classLoader.loadClass(serviceBean.impClass), plugin,
                        plugin.classLoader.loadClass(interfaceClass) as Class<Any>, true
                    )
                    /* if (instance != null) {
                         val implClass = instance.javaClass
                         if (Modifier.isFinal(implClass.modifiers)) {
                             serviceInstanceHotCache.putIfAbsent(implClass, instance)
                         }
                     }*/

                } catch (e: Exception) {
                    // isServicePreloadingCancelled = true
                    throw e
                }
            })
        }
    }

    return Pair(
        CompletableFuture.runAsync({
            ForkJoinTask.invokeAll(asyncPreloadedServices)

        }, ForkJoinPool.commonPool()),
        CompletableFuture.runAsync({
            ForkJoinTask.invokeAll(syncPreloadedServices)
        }, ForkJoinPool.commonPool())
    )

}

@JvmOverloads
fun preloadServices(
    plugins: List<PluginDescriptorImpl>,
    container: IDEApplication,
    activityPrefix: String,
    onlyIfAwait: Boolean = false
): CompletableFuture<Void?> {
    val result = preloadServicesImpl(plugins, container, activityPrefix, onlyIfAwait)

    fun logError(future: CompletableFuture<Void?>): CompletableFuture<Void?> {
        return future
            .whenComplete { _, error ->
                if (error != null && error !is ProcessCanceledException) {
                    println(error)
                    //StartupAbortedException.processException(error)
                }
            }
    }

    logError(result.first)
    return logError(result.second)
}

fun callAppInitialized(app: IDEApplicationImpl): List<ForkJoinTask<*>> {
    val extensionArea = app.extensionArea
    val extensionPoint =
        extensionArea.getExtensionPoint<ApplicationInitializedListener>("com.intellij.applicationInitializedListener")
                as ExtensionPointImpl
    val result = ArrayList<ForkJoinTask<*>>(extensionPoint.size())
    extensionPoint.processImplementations(/* shouldBeSorted = */ false) { supplier, _ ->
        result.add(ForkJoinTask.adapt {
            try {
                supplier.get()?.componentsInitialized()
            } catch (e: Throwable) {
                LOG.error(e)
            }
        })
    }
    extensionPoint.reset()
    return result
}


fun createAppLocatorFile() {
    val locatorFile = Path(PathManager.homePath)
    try {
        locatorFile.parent?.createDirectories()
        //Files.writeString(locatorFile, PathManager.getHomePath(), StandardCharsets.UTF_8)
    }
    catch (e: IOException) {
        LOG.warn("Can't store a location in '$locatorFile'", e)
    }
}

private fun startApp(
    app: IDEApplicationImpl,
    starter: IdeStarter,
    plugins: List<PluginDescriptorImpl>
) {
    // initSystemProperties or RegistryKeyBean.addKeysFromPlugins maybe not yet performed,
    // but it is ok because registry is not and should be not used
    initConfigurationStore(app)
    val preloadSyncServiceFuture = preloadServices(plugins, app, activityPrefix = "")

    CompletableFuture.allOf(preloadSyncServiceFuture/*, StartupUtil.getServerFuture()*/)
        .thenComposeAsync({
            val pool = ForkJoinPool.commonPool()

            val future = CompletableFuture.runAsync({
                ForkJoinTask.invokeAll(callAppInitialized(app))

            }, pool)

            pool.execute {
                createAppLocatorFile()
            }


            future
        }, {
            // if `loadComponentInEdtFuture` is completed after `preloadSyncServiceFuture`,
            // then this task will be executed in EDT, so force execution out of EDT
            /*  if (app.isDispatchThread) {
                  ForkJoinPool.commonPool().execute(it)
              }
              else {*/
            it.run()
            // }
        })
        .thenRun {
            starter.main()
        }
        .exceptionally {
            //StartupAbortedException.processException(it)
            null
        }
}
