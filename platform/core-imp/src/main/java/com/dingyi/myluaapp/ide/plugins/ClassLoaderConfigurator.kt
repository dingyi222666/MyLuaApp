package com.dingyi.myluaapp.ide.plugins


import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.ide.android.cl.AndroidParentClassLoader
import com.dingyi.myluaapp.ide.plugins.cl.AllPluginClassLoader
import com.dingyi.myluaapp.ide.plugins.cl.PluginClassLoader
import com.dingyi.myluaapp.ide.plugins.cl.SystemClassLoader
import com.dingyi.myluaapp.openapi.application.PathManager
import com.dingyi.myluaapp.openapi.dsl.plugin.service.ServiceDslBuilder
import com.dingyi.myluaapp.openapi.extensions.PluginId
import org.jetbrains.annotations.ApiStatus
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.nio.file.Path
import java.util.*

@ApiStatus.Internal
class ClassLoaderConfigurator(
    private val usePluginClassLoader: Boolean, /* grab classes from platform loader only if nothing is found in any of plugin dependencies */
    private val coreLoader: ClassLoader,
    val idMap: Map<PluginId, PluginDescriptorImpl>,
    /*  private val additionalLayoutMap: Map<String, Array<String>>*/
) {


    // temporary list to produce arrays (avoid allocation for each plugin)
    private val packagePrefixes = ArrayList<String>()

    private val allClassLoader = AllPluginClassLoader

    // todo for dynamic reload this guard doesn't contain all used plugin prefixes
    private val pluginPackagePrefixUniqueGuard = HashSet<String>()

    fun configure(mainDependent: PluginDescriptorImpl) {
        val pluginPackagePrefix = mainDependent.packagePrefix
        if (pluginPackagePrefix != null && !pluginPackagePrefixUniqueGuard.add(pluginPackagePrefix)) {
            throw PluginException(
                "Package prefix $pluginPackagePrefix is already used",
                mainDependent.pluginId
            )
        }


        val mainDependentClassLoader = /*if (mainDependent.isUseIdeaClassLoader) {
            configureUsingSystemClassloader(classPath, mainDependent)
        } else {*/
            createPluginClassLoader(mainDependent)
        /*  }*/

        mainDependent._classLoader = mainDependentClassLoader

        configurePlugin(mainDependent, mainDependentClassLoader)

    }

    private fun createPluginClassLoader(descriptor: PluginDescriptorImpl): ClassLoader {
        return createPluginClassLoader(
            AllPluginClassLoader, descriptor, coreLoader
        )
    }

    private fun configurePlugin(
        /*  dependencyInfo: PluginDependency,*/
        descriptor: PluginDescriptorImpl,
        mainDependentClassLoader: ClassLoader,

        ) {

        val pluginPackagePrefix = descriptor.packagePrefix
        if (pluginPackagePrefix == null) {
            if (descriptor.packagePrefix != null) {
                throw PluginException(
                    "Sub descriptor must specify package if it is specified for main plugin descriptor " +
                            "(parentPackagePrefix=${descriptor.packagePrefix})",
                    descriptor.id
                )
            }
        } else {
            if (pluginPackagePrefix == descriptor.packagePrefix) {
                throw PluginException(
                    "Sub descriptor must not specify the same package as main plugin descriptor",
                    descriptor.id
                )
            }

            if (descriptor.packagePrefix == null) {
                val parentId = descriptor.id.idString
                if (!(parentId == "Docker" ||
                            parentId == "org.jetbrains.plugins.ruby" ||
                            parentId == "org.intellij.grails" ||
                            parentId == "JavaScript")
                ) {
                    throw PluginException(
                        "Sub descriptor must not specify package if one is not specified for main plugin descriptor",
                        descriptor.id
                    )
                }
            }
            if (!pluginPackagePrefixUniqueGuard.add(pluginPackagePrefix)) {
                throw PluginException(
                    "Package prefix $pluginPackagePrefix is already used",
                    descriptor.id
                )
            }
            packagePrefixes.clear()
            collectPackagePrefixes(descriptor, packagePrefixes)
            // no package prefixes if only bean extension points are configured
            if (packagePrefixes.isEmpty()) {
                log.debug(
                    "Optional descriptor $descriptor contains only bean extension points or light services"
                )
            }
        }

        val dependency = idMap.get(descriptor.pluginId)
        if (dependency == null || !dependency.isEnabled) {
            return
        }


    }


}

// this list doesn't duplicate of PluginXmlFactory.CLASS_NAMES - interface related must be not here
private val IMPL_CLASS_NAMES = setOf(
    /*  arrayOf(*/
    "implementation", "implementationClass", "builderClass",
    "serviceImplementation", "class", "className",
    "instance", "implementation-class"
    /* )*/
)

// do not use class reference here
@Suppress("SSBasedInspection")
private val log: Logger
    get() = Logger.getInstance("#com.intellij.ide.plugins.PluginManager")

// static to ensure that anonymous classes will not hold ClassLoaderConfigurator
private fun createPluginClassLoader(
    parentLoader: AndroidParentClassLoader,
    descriptor: PluginDescriptorImpl,
    /* urlClassLoaderBuilder: UrlClassLoader.Builder,*/
    coreLoader: ClassLoader,
    /* resourceFileFactory: ClassPath.ResourceFileFactory?*/
): ClassLoader {

    val asFile = descriptor.path.toFile()

    return if (asFile.isFile) {
        val pluginLibraryPath = PathManager
            .getPluginLibPath(descriptor).toFile()


    } else {
        SystemClassLoader(
            coreLoader, descriptor
        )
    }


}


private fun isClassloaderPerDescriptorEnabled(descriptor: PluginDescriptorImpl): Boolean {
    return ClassLoaderConfigurationData.isClassloaderPerDescriptorEnabled(
        descriptor.id,
        descriptor.packagePrefix
    )
}

private fun collectPackagePrefixes(
    dependent: PluginDescriptorImpl,
    packagePrefixes: MutableList<String>
) {
    // from extensions
    dependent.unsortedEpNameToExtensionElements.values.forEach { extensionDescriptors ->
        for (extensionDescriptor in extensionDescriptors) {
            if (extensionDescriptor.implementation != null) {
                addPackageByClassNameIfNeeded(
                    extensionDescriptor.implementation!!,
                    packagePrefixes
                )
                continue
            }

            /* val element = extensionDescriptor.element ?: continue
             if (!element.attributes.isEmpty()) {
                 continue
             }

             for (attributeName in IMPL_CLASS_NAMES) {
                 val className = element.getAttributeValue(attributeName)
                 if (className != null && !className.isEmpty()) {
                     addPackageByClassNameIfNeeded(className, packagePrefixes)
                     break
                 }
             } */
        }
    }

    // from services
    collectFromServices(dependent.services, packagePrefixes)
    /*  collectFromServices(dependent.projectContainerDescriptor, packagePrefixes)
      collectFromServices(dependent.moduleContainerDescriptor, packagePrefixes)*/
}

private fun addPackageByClassNameIfNeeded(name: String, packagePrefixes: MutableList<String>) {
    for (packagePrefix in packagePrefixes) {
        if (name.startsWith(packagePrefix)) {
            return
        }
    }

    // for classes like com.intellij.thymeleaf.lang.ThymeleafParserDefinition$SPRING_SECURITY_EXPRESSIONS
    // we must not try to load the containing package
    if (name.indexOf('$') != -1) {
        packagePrefixes.add(name)
        return
    }

    val lastPackageDot = name.lastIndexOf('.')
    if (lastPackageDot > 0 && lastPackageDot != name.length) {
        addPackagePrefixIfNeeded(packagePrefixes, name.substring(0, lastPackageDot + 1))
    }
}

private fun addPackagePrefixIfNeeded(
    packagePrefixes: MutableList<String>,
    packagePrefix: String
) {
    for (i in packagePrefixes.indices) {
        val existingPackagePrefix = packagePrefixes.get(i)
        if (packagePrefix.startsWith(existingPackagePrefix)) {
            return
        } else if (existingPackagePrefix.startsWith(packagePrefix) && existingPackagePrefix.indexOf(
                '$'
            ) == -1
        ) {
            packagePrefixes.set(i, packagePrefix)
            for (j in packagePrefixes.size - 1 downTo i + 1) {
                if (packagePrefixes.get(j).startsWith(packagePrefix)) {
                    packagePrefixes.removeAt(j)
                }
            }
            return
        }
    }
    packagePrefixes.add(packagePrefix)
}

private fun collectFromServices(
    containerDescriptor: ServiceDslBuilder?,
    packagePrefixes: MutableList<String>
) {

    if (containerDescriptor == null) {
        return
    }

    for (service in containerDescriptor.applicationLevelServices + containerDescriptor.projectLevelServices) {
        // testServiceImplementation is ignored by intention

        val className = service.key.let {
            if (it is Class<*>) {
                it.name
            } else it.toString()
        }

        /*service.serviceImplementation?.let {
            addPackageByClassNameIfNeeded(it, packagePrefixes)
        }
        service.headlessImplementation?.let {*/
        addPackageByClassNameIfNeeded(className, packagePrefixes)

    }
}

