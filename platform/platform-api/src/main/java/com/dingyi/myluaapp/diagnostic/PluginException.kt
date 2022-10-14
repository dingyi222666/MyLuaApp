package com.dingyi.myluaapp.diagnostic

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.extensions.PluginId


/**
 *
 * Represents an internal error caused by a plugin. It may happen if the plugin's code fails with an exception, or if the plugin violates
 * some contract of IntelliJ Platform. If such exceptions are thrown or logged via [Logger.error]
 * method and reported to JetBrains by user, they may be automatically attributed to corresponding plugins.
 *
 *
 * If the problem is caused by a class, use [.createByClass] to create
 * an instance. If the problem is caused by an extension, implement [com.intellij.openapi.extensions.PluginAware] in its extension class
 * to get the plugin ID.
 */
class PluginException : RuntimeException, ExceptionWithAttachments {
    private val myPluginId: PluginId?
    private val myAttachments: List<Attachment>

    constructor(message: String, cause: Throwable, pluginId: PluginId) : super(message, cause) {
        myPluginId = pluginId
        myAttachments = emptyList()
    }

    constructor(e: Throwable, pluginId: PluginId) : super(e.message, e) {
        myPluginId = pluginId
        myAttachments = emptyList()
    }

    constructor(message: String, pluginId: PluginId?) : super(message) {
        myPluginId = pluginId
        myAttachments = emptyList()
    }

    constructor(
        message: String,
        pluginId: PluginId?,
        attachments: List<Attachment>
    ) : super(message) {
        myPluginId = pluginId
        myAttachments = attachments
    }

    constructor(
        message: String,
        cause: Throwable,
        pluginId: PluginId?,
        attachments: List<Attachment>
    ) : super(message, cause) {
        myPluginId = pluginId
        myAttachments = attachments
    }

    val pluginId: PluginId?
        get() = myPluginId

    // do not add suffix with plugin id if plugin info is already in message
    override val message: String
        get() {
            val message = super.message
            // do not add suffix with plugin id if plugin info is already in message
            return if (myPluginId == null || message != null && message.contains("PluginDescriptor(")) {
                checkNotNull(message)
            } else {
                (message ?: "null") + " [Plugin: " + myPluginId + "]"
            }
        }

    override val attachments: Array<Attachment>
        get() = myAttachments.toTypedArray()



    companion object {
        /**
         * Creates an exception caused by a problem in a plugin's code.
         * @param pluginClass a problematic class which caused the error
         */
        fun createByClass(
            errorMessage: String,
            cause: Throwable?,
            pluginClass: Class<*>
        ): PluginException {
            return PluginProblemReporter.instance
                .createPluginExceptionByClass(errorMessage, cause, pluginClass)
        }

        /**
         * Creates an exception caused by a problem in a plugin's code, takes error message from the cause exception.
         * @param pluginClass a problematic class which caused the error
         */
        fun createByClass(cause: Throwable, pluginClass: Class<*>): PluginException {
            val message = cause.message
            return PluginProblemReporter.instance
                .createPluginExceptionByClass(message ?: "", cause, pluginClass)
        }

        /**
         * Log an error caused by a problem in a plugin's code.
         * @param pluginClass a problematic class which caused the error
         */
        fun logPluginError(
            logger: Logger,
            errorMessage: String,
            cause: Throwable?,
            pluginClass: Class<*>
        ) {
            logger.error(createByClass(errorMessage, cause, pluginClass))
        }

        fun reportDeprecatedUsage(signature: String, details: String) {
            val message =
                "'$signature' is deprecated and going to be removed soon. $details"
            Logger.getInstance(PluginException::class.java).error(message)
        }

        fun reportDeprecatedDefault(violator: Class<*>, methodName: String, details: String) {
            val message =
                "The default implementation of method '$methodName' is deprecated, you need to override it in '$violator'. $details"
            Logger.getInstance(violator).error(createByClass(message, null, violator))
        }
    }
}