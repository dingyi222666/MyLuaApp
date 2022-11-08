package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.openapi.dsl.plugin.actions.ActionsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.listener.ListenerDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.service.ServiceDslBuilder
import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointDescriptor
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.ApiStatus
import java.time.LocalDate


@ApiStatus.Internal
class RawPluginDescriptor {

    @JvmField
    var id: String? = null

    @JvmField
    internal var name: String? = null

    @JvmField
    internal var author: String? = null


    @JvmField
    internal var version: String? = null

    @JvmField
    internal var actions: ActionsDslBuilder? = null

    @JvmField
    internal var description: String? = null

    @JvmField
    internal var minSdkVersion: Int? = null

    @JvmField
    var listeners: MutableList<ListenerDslBuilder>? = null


    @JvmField
    var services: ServiceDslBuilder? = null


    @JvmField
    var epNameToExtensions: List<ExtensionsDslBuilder>? = null


    @JvmField
    var epNameToExtensionPoints: List<ExtensionPointBuilder>? = null


    @JvmField
    var applicationClassName: String? = null

}
