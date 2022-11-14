package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.ide.plugins.cl.DslLoaderClassLoader
import com.dingyi.myluaapp.openapi.dsl.plugin.PluginConfig
import com.dingyi.myluaapp.plaform.util.plugins.DataLoader
import org.xmlpull.v1.XmlPullParser
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString



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

    val descriptorClassLoader = if (filePath == null) {
        systemClassLoader
    } else {
        DslLoaderClassLoader(
            filePath.absolutePathString(),
            systemClassLoader
        )
    }


    descriptor = loadModuleDescriptor(
        descriptorClassLoader,
        mainClassName,
        descriptor
    )


    tryReadManifestFile(descriptor, dataLoader)


    return descriptor

}


private fun tryReadManifestFile(descriptor: RawPluginDescriptor, dataLoader: DataLoader) {

    val androidManifestSource = dataLoader.load("AndroidManifest")

    //create xml block instance
    val blockClass = Class.forName("android.content.res.XmlBlock")
    val blockConstructor = blockClass.getDeclaredConstructor(ByteArray::class.java)
    blockConstructor.isAccessible = true
    val block = blockConstructor.newInstance(androidManifestSource)

    //create xml parser

    val pullParser: XmlPullParser =
        blockClass.getDeclaredMethod("newParser")
            .apply {
                isAccessible = true
            }
            .invoke(block) as XmlPullParser

    var applicationClass = ""

    var packageName = ""

    var eventType = pullParser.eventType



    while (eventType != XmlPullParser.END_DOCUMENT) {

        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (pullParser.name) {
                    "application" -> {
                        for (i in 0 until pullParser.attributeCount) {
                            val attributeName = pullParser.getAttributeName(i)
                            if (attributeName == "name") {
                                applicationClass = pullParser.getAttributeValue(i)
                                break
                            }
                        }
                    }

                    "manifest" -> {
                        for (i in 0 until pullParser.attributeCount) {
                            val attributeName = pullParser.getAttributeName(i)
                            if (attributeName == "package") {
                                packageName = pullParser.getAttributeValue(i)
                                break
                            }
                        }
                    }
                }

            }
        }

        eventType = pullParser.next()
    }


    if (applicationClass.startsWith(".")) {
        applicationClass = packageName + applicationClass
    }

    if (applicationClass.isNotEmpty()) {
        descriptor.applicationClassName = applicationClass
    }


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
            `package` = config.packagePrefix
        }

    } else {
        error("")
    }

    return rawPluginDescriptor
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