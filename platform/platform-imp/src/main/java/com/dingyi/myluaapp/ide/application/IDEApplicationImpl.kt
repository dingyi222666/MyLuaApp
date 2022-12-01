package com.dingyi.myluaapp.ide.application


import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.ide.plugins.PluginDescriptorImpl
import com.dingyi.myluaapp.openapi.actions.internal.ActionManagerImpl
import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.dsl.plugin.listener.ListenerDslBuilder
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.service.get
import com.dingyi.myluaapp.openapi.service.internal.DefaultServiceRegistry
import com.dingyi.myluaapp.util.messages.ListenerDescriptor
import com.dingyi.myluaapp.util.messages.MessageBusOwner
import com.dingyi.myluaapp.util.messages.imp.MessageBusEx
import com.dingyi.myluaapp.util.messages.imp.RootBus
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal class IDEApplicationImpl : DefaultServiceRegistry("ApplicationServices"), IDEApplication,
    MessageBusOwner, Disposable {

    private val myLastDisposable = Disposer.newDisposable() // the last to be disposed

    private val applicationCoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val applicationMessageBus = RootBus(this)


    private val allProjectRegisterTask = mutableListOf<RegisterTask>()

    init {
        asRegistration().apply {
            add(getJavaClass<IDEApplication>(), this@IDEApplicationImpl)
        }

        // reset back to null only when all components already disposed
        ApplicationManager.setApplication(this, myLastDisposable);
    }

    override fun getApplicationCoroutineScope(): CoroutineScope = applicationCoroutineScope

    override fun saveAll() {

    }

    override fun saveSettings() {

    }

    override fun exit() {

    }


    fun registerComponents(
        plugins: List<PluginDescriptorImpl>,
        listenerCallbacks: MutableList<Runnable>?
    ) {

        for (plugin in plugins) {
            // register services
            plugin.services?.apply {
                applicationLevelServices.forEach { (_, u) ->
                    add(plugin.classLoader.loadClass(u.toString()))
                }

                doProjectRegisterTask {
                    projectLevelServices.forEach { (_, u) ->
                        it.getServiceRegistry().asRegistration()
                            .add(plugin.pluginClassLoader.loadClass(u.toString()))
                    }
                }

            }

            //register listener
            val applicationListenerDescriptor =
                mutableMapOf<String, MutableList<ListenerDescriptor>>()

            val projectListenerDescriptor = mutableMapOf<String, MutableList<ListenerDescriptor>>()

            plugin.listeners?.forEach {
                it.allListener.forEach { listenerImplementationBuilder ->
                    if (it.level == ListenerDslBuilder.Level.Application) {
                        applicationListenerDescriptor
                    } else {
                        projectListenerDescriptor
                    }.getOrPut(listenerImplementationBuilder.topic) { mutableListOf() }
                        .apply {
                            add(
                                ListenerDescriptor(
                                    listenerClassName = listenerImplementationBuilder.targetClass.toString(),
                                    topicClassName = listenerImplementationBuilder.topic,
                                    pluginDescriptor = plugin
                                )
                            )
                        }

                }
            }

            applicationMessageBus.setLazyListeners(applicationListenerDescriptor)

            doProjectRegisterTask {
                val messageBus = it.getMessageBus()
                if (messageBus is MessageBusEx) {
                    messageBus.setLazyListeners(projectListenerDescriptor)
                }
            }


            //register extensionPoints

            val extensionPoints = HashMap(
                (extensionArea as ExtensionsAreaImpl).extensionPoints
            )

            plugin.epNameToExtensionPoints.forEach { (name, descriptors) ->
                ExtensionsAreaImpl.createExtensionPoints(
                    descriptors, this, extensionPoints, plugin
                )
            }

            (extensionArea as ExtensionsAreaImpl).extensionPoints = extensionPoints


            //register extensions

            for ((name, descriptors) in plugin.epNameToExtensions) {
                val point = extensionPoints.get(name) ?: continue

                if (descriptors.isNotEmpty()) {
                    point.registerExtensions(descriptors, plugin, listenerCallbacks)
                }

            }

            //register actions

            get<ActionManagerImpl>().registerPluginAction(
                plugin
            )


        }


    }

    override var isDisposed: Boolean = false
    override fun createListener(descriptor: ListenerDescriptor): Any {
        val targetClass = descriptor.pluginDescriptor.pluginClassLoader?.loadClass(
            descriptor
                .listenerClassName
        )
        // no inject
        return targetClass?.newInstance() as Any
    }

    override fun getMessageBus() = applicationMessageBus

    override fun dispose() {
        super.dispose()
        isDisposed = true
    }


    private fun doProjectRegisterTask(task: RegisterTask) {
        allProjectRegisterTask.add(task)
    }

    fun interface RegisterTask {
        fun execute(project: Project)
    }
}