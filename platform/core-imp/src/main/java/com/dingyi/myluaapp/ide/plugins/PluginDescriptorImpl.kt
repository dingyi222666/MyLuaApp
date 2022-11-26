package com.dingyi.myluaapp.ide.plugins


import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.ide.ui.android.bundle.AndroidBundle
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionsDslBuilder
import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.dingyi.myluaapp.openapi.extensions.dsl.toExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import com.dingyi.myluaapp.plaform.util.plugins.DataLoader
import org.jetbrains.annotations.ApiStatus
import java.io.File
import java.nio.file.Path
import java.util.Collections


private val LOG: Logger
    get() = PluginManagerCore.logger

@ApiStatus.Internal
class PluginDescriptorImpl(
    raw: RawPluginDescriptor,
    val path: Path,
    id: PluginId?,
    val isBundled: Boolean = false
) : PluginDescriptor {
    val id: PluginId = id ?: PluginId.getId(
        raw.id ?: raw.name ?: throw RuntimeException("Nor id, neither name are specified")
    )
    private val name = raw.name ?: id?.idString ?: raw.id

    @JvmField val packagePrefix = raw.`package`

    @Volatile
    private var description: String? = raw.description

    internal var version = raw.version

    internal val author = raw.author

     val minSdkVersion = raw.minSdkVersion

    init {

    }


    @JvmField
    var _classLoader: ClassLoader? = null

    @JvmField
    val actions = raw.actions

    // extension point name -> list of extension descriptors
    val epNameToExtensions: Map<String, List<ExtensionDescriptor>>? =
        mutableMapOf<String, List<ExtensionDescriptor>>().apply {
            raw.epNameToExtensions?.map { extensionsDslBuilder ->
                putAll(extensionsDslBuilder.allImplementation.mapValues { it.value.map { it.toExtensionDescriptor() } })
            }
        }


    @JvmField
     val listeners = raw.listeners

    @JvmField
     val services = raw.services


    private var isEnabled = true


    override fun getPluginPath() = path


    fun readExternal(
        raw: RawPluginDescriptor,
        pathResolver: PathResolver,
        context: DescriptorListLoadingContext,
        isSub: Boolean,
        dataLoader: DataLoader
    ) {
        // include module file descriptor if not specified as `depends` (old way - xi:include)
        // must be first because merged into raw descriptor
        /* if (!isSub) {
             moduleLoop@ for (module in contentDescriptor.modules) {
                 val descriptorFile = module.configFile ?: "${module.name}.xml"
                 val oldDepends = raw.depends
                 if (oldDepends != null) {
                     for (dependency in oldDepends) {
                         if (descriptorFile == dependency.configFile) {
                             // ok, it is specified in old way as depends tag - skip it
                             continue@moduleLoop
                         }
                     }
                 }

                 pathResolver.resolvePath(context, dataLoader, descriptorFile, raw)
                     ?: throw RuntimeException("Plugin $this misses optional descriptor $descriptorFile")
                 module.isInjected = true
             }
         }

         if (raw.resourceBundleBaseName != null) {
             if (id == PluginManagerCore.CORE_ID) {
                 LOG.warn(
                     "<resource-bundle>${raw.resourceBundleBaseName}</resource-bundle> tag is found in an xml descriptor" +
                             " included into the platform part of the IDE but the platform part uses predefined bundles " +
                             "(e.g. ActionsBundle for actions) anyway; this tag must be replaced by a corresponding attribute in some inner tags " +
                             "(e.g. by 'resource-bundle' attribute in 'actions' tag)"
                 )
             }
             if (resourceBundleBaseName != null && resourceBundleBaseName != raw.resourceBundleBaseName) {
                 LOG.warn(
                     "Resource bundle redefinition for plugin $id. " +
                             "Old value: $resourceBundleBaseName, new value: ${raw.resourceBundleBaseName}"
                 )
             }
             resourceBundleBaseName = raw.resourceBundleBaseName
         }*/

        if (version == null) {
            version = context.defaultVersion.toString()
        }

        if (context.isPluginDisabled(id)) {
            markAsIncomplete(context, null, null)
        } /*else {
            for (pluginDependency in dependencyDescriptor.plugins) {
                if (context.isPluginDisabled(pluginDependency.id)) {
                    markAsIncomplete(
                        context,
                        pluginDependency.id,
                        shortMessage = "plugin.loading.error.short.depends.on.disabled.plugin"
                    )
                } else if (context.result.isBroken(pluginDependency.id)) {
                    markAsIncomplete(
                        context = context,
                        disabledDependency = null,
                        shortMessage = "plugin.loading.error.short.depends.on.broken.plugin",
                        pluginId = pluginDependency.id
                    )
                }
            }
        }*/

        /* processOldDependencies(
             descriptor = this,
             context = context,
             pathResolver = pathResolver,
             dependencies = pluginDependencies,
             dataLoader = dataLoader
         )
    */
        checkCompatibility(context)
    }


    private fun checkCompatibility(context: DescriptorListLoadingContext) {
        /* if (isBundled || (sinceBuild == null && untilBuild == null)) {
             return
         }
    */
        val error = PluginManagerCore.checkBuildNumberCompatibility(
            this,
            context.result.productBuildNumber.get()
        ) ?: return

        // error will be added by reportIncompatiblePlugin
        markAsIncomplete(context = context, disabledDependency = null, shortMessage = null)
        context.result.reportIncompatiblePlugin(this, error)
    }

    private fun markAsIncomplete(
        context: DescriptorListLoadingContext,
        disabledDependency: PluginId?,
        shortMessage: Int?,
        pluginId: PluginId? = disabledDependency
    ) {
        /*    if (isIncomplete) {
                return
            }

            isIncomplete = true*/
        isEnabled = false

        val pluginError = if (shortMessage == null) {
            null
        } else {
            RuntimeException(
                AndroidBundle.coreBundle.message(shortMessage, pluginId!!)
            )
        }
        context.result.addIncompletePlugin(this, pluginError)
    }

    fun collectExtensionPoints() {

    }

    @ApiStatus.Internal
    fun registerExtensions(
        nameToPoint: Map<String, ExtensionPointImpl<*>>,
        containerDescriptor: ExtensionsDslBuilder?,
        listenerCallbacks: MutableList<Runnable>?,
        area: ExtensionPointBuilder.Area? = ExtensionPointBuilder.Area.APPLICATION
    ) {
        containerDescriptor?.allImplementation?.let { extensionsMap ->
            if (!extensionsMap.isEmpty()) {
                @Suppress("JavaMapForEach")
                extensionsMap.forEach { pointName, list ->
                    nameToPoint.get(pointName)?.registerExtensions(
                        list.map { it.toExtensionDescriptor() }, this, listenerCallbacks
                    )
                }
            }
            return
        }

        val unsortedMap = epNameToExtensions ?: return

        // app container: in most cases will be only app-level extensions - to reduce map copying, assume that all extensions are app-level and then filter out
        // project container: rest of extensions wil be mostly project level
        // module container: just use rest, area will not register unrelated extension anyway as no registered point

        /*   if (area = ExtensionPointBuilder.Area.APPLICATION) {
                val registeredCount = doRegisterExtensions(unsortedMap, nameToPoint, listenerCallbacks)
                containerDescriptor.distinctExtensionPointCount = registeredCount

                if (registeredCount == unsortedMap.size) {
                    projectContainerDescriptor.extensions = Collections.emptyMap()
                    moduleContainerDescriptor.extensions = Collections.emptyMap()
                }
            } else if (containerDescriptor == projectContainerDescriptor) {
                val registeredCount = doRegisterExtensions(unsortedMap, nameToPoint, listenerCallbacks)
                containerDescriptor.distinctExtensionPointCount = registeredCount

                if (registeredCount == unsortedMap.size) {
                    containerDescriptor.extensions = unsortedMap
                    moduleContainerDescriptor.extensions = Collections.emptyMap()
                } else if (registeredCount == (unsortedMap.size - appContainerDescriptor.distinctExtensionPointCount)) {
                    moduleContainerDescriptor.extensions = Collections.emptyMap()
                }
            } else {*/
        /*   val registeredCount =*/

        doRegisterExtensions(unsortedMap, nameToPoint, listenerCallbacks)

        /*  if (registeredCount == 0) {
              moduleContainerDescriptor.extensions = Collections.emptyMap()
          }
      }*/
    }

    private fun doRegisterExtensions(
        unsortedMap: Map<String, List<ExtensionDescriptor>>,
        nameToPoint: Map<String, ExtensionPointImpl<*>>,
        listenerCallbacks: MutableList<Runnable>?
    ): Int {
        var registeredCount = 0
        for (entry in unsortedMap) {
            val point = nameToPoint.get(entry.key) ?: continue
            point.registerExtensions(entry.value, this, listenerCallbacks)
            registeredCount++
        }
        return registeredCount
    }

    override fun getDescription(): String {
        return description.toString()
    }


    override fun getName(): String = name!!


    val unsortedEpNameToExtensionElements: Map<String, List<ExtensionDescriptor>>
        get() {
            return Collections.unmodifiableMap(epNameToExtensions ?: return Collections.emptyMap())
        }


    override fun getPluginId() = id

    override fun getPluginClassLoader(): ClassLoader = _classLoader ?: javaClass.classLoader
    override fun getAuthor(): String {
        return author.toString()
    }

    override fun getMinSdkVersion(): Int {
        return minSdkVersion ?: 0
    }

    override fun getVersion(): String {
        return version.toString()
    }


    override fun isEnabled() = isEnabled

    override fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }


    override fun equals(other: Any?) =
        this === other || id == if (other is PluginDescriptorImpl) other.id else null

    override fun hashCode() = id.hashCode()

    override fun toString(): String {
        return "PluginDescriptor(name=$name, id=$id, " +
                "path=${pluginPathToUserString(path)}, version=$version)"
    }
}

// don't expose user home in error messages
internal fun pluginPathToUserString(file: Path): String {
    return file.toString().replace(
        "${System.getProperty("user.home")}${File.separatorChar}",
        "~${File.separatorChar}"
    )
}

