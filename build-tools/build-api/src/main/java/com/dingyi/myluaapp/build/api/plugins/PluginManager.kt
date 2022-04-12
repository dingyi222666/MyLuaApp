package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Action


/**
 * Facilitates applying plugins and determining which plugins have been applied to a [PluginAware] object.
 *
 * @see PluginAware
 *
 * @since 2.3
 */
interface PluginManager {
    /**
     * Applies the plugin with the given ID. Does nothing if the plugin has already been applied.
     *
     *
     * Plugins in the `"org.gradle"` namespace can be applied directly via name.
     * That is, the following two lines are equivalent…
     * <pre class='autoTested'>
     * pluginManager.apply "org.gradle.java"
     * pluginManager.apply "java"
    </pre> *
     *
     * @param pluginId the ID of the plugin to apply
     * @since 2.3
     */
    fun apply(pluginId: String)

    /**
     * Applies the given plugin. Does nothing if the plugin has already been applied.
     *
     *
     * The given class should implement the [org.gradle.api.Plugin] interface, and be parameterized for a compatible type of `this`.
     *
     *
     * The following two lines are equivalent…
     * <pre class='autoTested'>
     * pluginManager.apply org.gradle.api.plugins.JavaPlugin
     * pluginManager.apply "org.gradle.java"
    </pre> *
     *
     * @param type the plugin class to apply
     * @since 2.3
     */
    fun apply(type: Class<*>)

    /**
     * Returns the information about the plugin that has been applied with the given ID, or null if no plugin has been applied with the given ID.
     *
     *
     * Plugins in the `"org.gradle"` namespace (that is, core Gradle plugins) can be specified by either name (e.g. `"java"`) or ID `"org.gradle.java"`.
     * All other plugins must be queried for by their full ID (e.g. `"org.company.some-plugin"`).
     *
     *
     * Some Gradle plugins have not yet migrated to fully qualified plugin IDs.
     * Such plugins can be detected with this method by simply using the unqualified ID (e.g. `"some-third-party-plugin"`.
     *
     * @param id the plugin ID
     * @return information about the applied plugin, or `null` if no plugin has been applied with the given ID
     * @since 2.3
     */
    fun findPlugin(id: String): AppliedPlugin

    /**
     * Returns `true` if a plugin with the given ID has already been applied, otherwise `false`.
     *
     * @param id the plugin ID. See [.findPlugin] for details about this parameter.
     * @return `true` if the plugin has been applied
     * @since 2.3
     */
    fun hasPlugin(id: String): Boolean

    /**
     * Executes the given action when the specified plugin is applied.
     *
     *
     * If a plugin with the specified ID has already been applied, the supplied action will be executed immediately.
     * Otherwise, the action will executed immediately after a plugin with the specified ID is applied.
     *
     *
     * The given action is always executed after the plugin has been applied.
     *
     * @param id the plugin ID. See [.findPlugin] for details about this parameter.
     * @param action the action to execute if/when the plugin is applied
     * @since 2.3
     */
    fun withPlugin(
        id: String?,
        action: Action<in AppliedPlugin>
    )
}
