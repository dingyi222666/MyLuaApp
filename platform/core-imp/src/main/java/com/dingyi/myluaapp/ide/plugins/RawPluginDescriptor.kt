package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.ApiStatus
import java.time.LocalDate


@ApiStatus.Internal
class RawPluginDescriptor {
    @JvmField var id: String? = null
    @JvmField internal var name: String? = null


    @JvmField internal var version: String? = null

    @JvmField internal var actions: MutableList<ActionsDslBuilder>? = null

    @JvmField var incompatibilities: MutableList<PluginId>? = null

    @JvmField val appContainerDescriptor = ContainerDescriptor()
    @JvmField val projectContainerDescriptor = ContainerDescriptor()
    @JvmField val moduleContainerDescriptor = ContainerDescriptor()

    @JvmField var epNameToExtensions: MutableMap<String, MutableList<ExtensionDescriptor>>? = null

    @JvmField internal var contentDescriptor = PluginContentDescriptor.EMPTY
    @JvmField internal var dependencyDescriptor = ModuleDependenciesDescriptor.EMPTY


}
