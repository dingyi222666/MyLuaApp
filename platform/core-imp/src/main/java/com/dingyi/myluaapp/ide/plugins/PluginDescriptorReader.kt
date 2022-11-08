package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.ide.plugins.cl.DslLoaderClassLoader
import com.dingyi.myluaapp.openapi.dsl.plugin.PluginConfig
import com.dingyi.myluaapp.plaform.util.plugins.DataLoader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.Reader
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString


/**
 * inputStream: InputStream,
 * readContext: DescriptorListLoadingContext,
 * pathResolver: PathResolver,
 * dataLoader: DataLoader,
 * readInto: RawPluginDescriptor?,
 * locationSource: String
 */

fun readModuleDescriptor(
    input: InputStream?,
    readContext: DescriptorListLoadingContext,
    pathResolver: PathResolver,
    dataLoader: DataLoader,
    readInto: RawPluginDescriptor?,
    locationSource: String?
): RawPluginDescriptor {


    var descriptor = readInto ?: RawPluginDescriptor()

    val mainClassName = input?.readBytes()?.decodeToString() ?: return readInto ?: error("")

    val filePath = locationSource?.let {
        Path(it)
    }

    val systemClassLoader = getJavaClass<RawPluginDescriptor>().classLoader

    descriptor = if (filePath == null) {
        loadModuleDescriptor(
            systemClassLoader,
            mainClassName,
            descriptor
        )
    } else {
        loadModuleDescriptor(
            DslLoaderClassLoader(
                filePath.absolutePathString(),
                systemClassLoader
            ),
            mainClassName, descriptor
        )
    }


    TODO("Read android manifest file")


}

private fun loadModuleDescriptor(
    classLoader: ClassLoader,
    className: String,
    descriptor: RawPluginDescriptor?
): RawPluginDescriptor {
    val loadedClass = classLoader.loadClass(className)

    val rawPluginDescriptor = descriptor ?: RawPluginDescriptor()

    if (loadedClass.isAssignableFrom(PluginConfig::class.java)) {
        val instance = loadedClass.newInstance() as PluginConfig
        val config = instance.config()

        rawPluginDescriptor.apply {
            actions = config.actions
            name = config.name
            author = config.author
            minSdkVersion = config.minSdkVersion
            listeners = config.listeners
            actions = config.actions
            services = config.services
            epNameToExtensions = config.extensions
            epNameToExtensionPoints = config.extensionPoints.allExtensionPointBuilder
            id = config.id
        }

    }

    //TODO: load error
    error("")

}

fun readModuleDescriptor(
    bytes: ByteArray?,
    readContext: DescriptorListLoadingContext,
    pathResolver: PathResolver,
    dataLoader: DataLoader,
    readInto: RawPluginDescriptor?,
    locationSource: String?
): RawPluginDescriptor = readModuleDescriptor(
    ByteArrayInputStream(bytes ?: byteArrayOf()),
    readContext,
    pathResolver,
    dataLoader,
    readInto,
    locationSource
)