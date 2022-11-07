package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.ide.plugins.PluginPathResolver.Companion.toLoadPath
import com.dingyi.myluaapp.plaform.util.plugins.DataLoader


internal class ClassPathXmlPathResolver(private val classLoader: ClassLoader) : PathResolver {
    override fun resolvePath(readContext: DescriptorListLoadingContext,
                             dataLoader: DataLoader,
                             relativePath: String,
                             readInto: RawPluginDescriptor?): RawPluginDescriptor? {
        val path = toLoadPath(relativePath, null)
        val resource = classLoader.getResourceAsStream(path)
        return readModuleDescriptor(input  = resource ?: return null,
            readContext = readContext,
            pathResolver = this,
            dataLoader = dataLoader,
           /* includeBase = null,*/
            readInto = readInto,
            locationSource = dataLoader.toString())
    }


}