package com.dingyi.myluaapp.ide.plugins.cl


import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import org.intellij.lang.annotations.MagicConstant
import java.nio.file.Path


interface PluginAwareClassLoader {
    val pluginDescriptor: PluginDescriptor
    val pluginId: PluginId
    val instanceId: Int

   /* val backgroundTime: Long*/
    val loadedClassCount: Long
  /*  val files: Collection<Path>*/

    @get:MagicConstant(intValues = [ACTIVE.toLong(), UNLOAD_IN_PROGRESS.toLong()])
    val state: Int

    /**
     * Loads class by name from this classloader and delegates loading to parent classloaders if and only if not found.
     */
    @Throws(ClassNotFoundException::class)
    fun tryLoadingClass(name: String, forceLoadFromSubPluginClassloader: Boolean): Class<*>?
    val packagePrefix: String?

    companion object {
        const val ACTIVE = 1
        const val UNLOAD_IN_PROGRESS = 2
    }
}
