package com.dingyi.myluaapp.openapi.dsl.plugin

import com.dingyi.myluaapp.openapi.dsl.plugin.actions.ActionsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.actions.actions
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionsDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.extensionPoints
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.extensions
import com.dingyi.myluaapp.openapi.dsl.plugin.listener.ListenerDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.listener.listeners
import com.dingyi.myluaapp.openapi.dsl.plugin.service.ServiceDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.service.services


class PluginDslBuilder internal constructor() {
    var version: String = ""

    var minSdkVersion = 0

    var name: String = ""


    var description: String = ""


    var descriptionWithMarkdown: String? = null


    lateinit var id: String

    var author: String = ""

    lateinit var services: ServiceDslBuilder
        internal set


    lateinit var actions: ActionsDslBuilder
        internal set

    lateinit var extensionPoints: ExtensionPointsDslBuilder
        internal set

    lateinit var extensions: MutableList<ExtensionsDslBuilder>
        internal set

    lateinit var listeners: MutableList<ListenerDslBuilder>
        internal set

    internal fun isInitializedExtensions() = this::extensions.isInitialized

    internal fun isInitializedListeners() = this::listeners.isInitialized

}

fun plugin(name: String, block: PluginDslBuilder.() -> Unit): PluginDslBuilder {
    return PluginDslBuilder()
        .apply {
            this.name = name
        }
        .also(block)
}
fun test() {
    plugin("JavaPlugin") {
        version = "114514"
        author = "dingyi"
        description = "Java Plugin"
        id = "com.dingyi.myPlugin"

        services {
            application("test") impl "666"
        }

        actions {

            actionGroup("cc") {
                id = "aaa"
                addToGroup("mainToolbar")

                action("666") {
                    id = "999"
                    description = "aaaa"
                    targetClass = "ccc "
                }
            }

            action("66") {
                targetClass = "xxx"
            }
        }

        extensionPoints {
            extensionPoint("666") {
                area = ExtensionPointBuilder.Area.APPLICATION
                interfaceClass = "999"
                "implementationClass" withImplements "jfdsfh"
            }
        }

        extensions("com.myluaapp") {
            "Projectxxx" implementation {
                implementation = "xxx"
            }
        }

        listeners(ListenerDslBuilder.Level.Application) {
            listener {
                targetClass = "xxx"
                topic = "zzz"
            }
        }

    }
}