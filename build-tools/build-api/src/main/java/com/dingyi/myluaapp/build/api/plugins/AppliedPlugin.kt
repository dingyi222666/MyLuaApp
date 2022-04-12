package com.dingyi.myluaapp.build.api.plugins


/**
 * Represents a plugin that has been applied.
 *
 *
 * Currently just provides information about the ID of the plugin.
 *
 * @see PluginAware
 *
 * @since 2.3
 */
interface AppliedPlugin {
    /**
     * The ID of the plugin.
     *
     *
     * An example of a plugin ID would be `"org.gradle.java"`.
     * This method always returns the fully qualified ID, regardless of whether the fully qualified ID was used to apply the plugin or not.
     *
     *
     * This value is guaranteed to be unique, for a given [org.gradle.api.plugins.PluginAware].
     *
     * @return the ID of the plugin
     */
    fun getId(): String

    /**
     * The namespace of the plugin.
     *
     *
     * An example of a plugin namespace would be `"org.gradle"` for the plugin with ID `"org.gradle.java"`.
     * This method always returns the namespace, regardless of whether the fully qualified ID was used to apply the plugin or not.
     *
     *
     * If the plugin has an unqualified ID, this method will return `null`.
     *
     * @return the namespace of the plugin
     */

    fun getNamespace(): String

    /**
     * The name of the plugin.
     *
     *
     * An example of a plugin name would be `"java"` for the plugin with ID `"org.gradle.java"`.
     * This method always returns the name, regardless of whether the fully qualified ID was used to apply the plugin or not.
     *
     *
     * If the plugin has an unqualified ID, this method will return the same value as [.getId].
     *
     * @return the name of the plugin
     */
    fun getName(): String
}
