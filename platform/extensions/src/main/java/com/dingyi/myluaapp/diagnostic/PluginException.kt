package com.dingyi.myluaapp.diagnostic


import com.dingyi.myluaapp.openapi.extensions.PluginId


import com.intellij.openapi.util.text.Strings
import org.jetbrains.annotations.NonNls


/**
 * Represents an internal error caused by a plugin. It may happen if the plugin's code fails with an exception, or if the plugin violates
 * some contract of IntelliJ Platform. If such exceptions are thrown or logged via [Logger.error]
 * method and reported to JetBrains by user, they may be automatically attributed to corresponding plugins.
 *
 *
 *  If the problem is caused by a class, use [.createByClass] to create
 * an instance. If the problem is caused by an extension, implement [com.intellij.openapi.extensions.PluginAware] in its extension class
 * to get the plugin ID.
 */
class PluginException : RuntimeException, ExceptionWithAttachments {
    private val myPluginId: PluginId?
    val _attachments: List<Attachment>

    override val attachments: Array<Attachment>
        get() = _attachments.toTypedArray()

    constructor(@NonNls message: String, cause: Throwable?, pluginId: PluginId?) : super(
        message,
        cause
    ) {
        myPluginId = pluginId
        _attachments = emptyList()
    }

    constructor(e: Throwable, pluginId: PluginId?) : super(e.message, e) {
        myPluginId = pluginId
        _attachments = emptyList()
    }

    constructor(@NonNls message: String, pluginId: PluginId?) : super(message) {
        myPluginId = pluginId
        _attachments = emptyList()

    }

    constructor(
        @NonNls message: String,
        pluginId: PluginId?,
        attachments: List<Attachment>
    ) : super(message) {
        myPluginId = pluginId
        this._attachments = attachments
    }

    val pluginId: PluginId?
        get() = myPluginId

    // do not add suffix with plugin id if plugin info is already in message
    @get:NonNls
    override val message: String
        get() {
            val message = super.message
            // do not add suffix with plugin id if plugin info is already in message
            return if (myPluginId == null || message != null && message.contains("PluginDescriptor(")) {
                message!!
            } else {
                Strings.notNullize(message) + " [Plugin: " + myPluginId + "]"
            }
        }


}
