package com.dingyi.myluaapp.ide.plugins.cl

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import org.jetbrains.annotations.ApiStatus
import java.net.URL
import java.nio.file.Path
import java.util.Enumeration
import java.util.concurrent.atomic.AtomicInteger

class SystemClassLoader(
    private val parentClassLoader: ClassLoader,
    pluginDescriptor: PluginDescriptor,
) : ClassLoader(
    parentClassLoader
), PluginAwareClassLoader {


    override val pluginDescriptor: PluginDescriptor

    // to simplify analyzing of heap dump (dynamic plugin reloading)
    override val pluginId: PluginId
    override val packagePrefix: String? = null


    private val coreLoader: ClassLoader
    override val instanceId: Int = PluginClassLoader.instanceIdProducer.incrementAndGet()
    override val loadedClassCount: Long
        get() = 1

    @get:ApiStatus.Internal
    @set:ApiStatus.Internal
    @Volatile
    override var state = PluginAwareClassLoader.ACTIVE
    override fun tryLoadingClass(
        name: String,
        forceLoadFromSubPluginClassloader: Boolean
    ): Class<*>? {
        return loadClass(name)
    }


    init {

        this.pluginDescriptor = pluginDescriptor
        pluginId = pluginDescriptor.getPluginId()
        this.coreLoader = parentClassLoader
        /*  if (PluginClassLoader::class.java.desiredAssertionStatus()) {
              for (parent: ClassLoader? in this.parents) {
                  if (parent === coreLoader) {
                      Logger.getInstance(PluginClassLoader::class.java).error(
                          "Core loader must be not specified in parents " +
                                  "(parents=" + Arrays.toString(parents) + ", coreLoader=" + coreLoader + ")"
                      )
                  }
              }
          }*/
        /* libDirectories = SmartList()
         if (pluginRoot != null) {
             val libDir = pluginRoot.resolve("lib")
             if (Files.exists(libDir)) {
                 libDirectories.add(libDir.toAbsolutePath().toString())
             }
         }*/
    }


    public override fun findClass(name: String?): Class<*>? {
        return parentClassLoader.loadClass(name)
    }

    public override fun findResource(name: String?): URL? {
        return parentClassLoader.getResource(name)
    }

    public override fun findResources(name: String?): Enumeration<URL>? {
        return parentClassLoader.getResources(name)
    }
}