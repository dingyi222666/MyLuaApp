package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Plugin


/**
 *
 * A `PluginContainer` is used to manage a set of [org.gradle.api.Plugin] instances applied to a
 * particular project.
 *
 *
 * Plugins can be specified using either an id or type. The id of a plugin is specified using a
 * META-INF/gradle-plugins/${id}.properties classpath resource.
 */
interface PluginContainer : PluginCollection<Plugin<*>> {
    /**
     * Has the same behavior as [.apply] except that the plugin is specified via its id. Not all
     * plugins have an id.
     *
     * @param id The id of the plugin to be applied.
     * @return The plugin which has been used against the project.
     */
    fun apply(id: String): Plugin<*>

    /**
     * Applies a plugin to the project. This usually means that the plugin uses the project API to add and modify the
     * state of the project. This method can be called an arbitrary number of times for a particular plugin type. The
     * plugin will be actually used only the first time this method is called.
     *
     * @param type The type of the plugin to be used
     * @return The plugin which has been used against the project.
     */
    fun <T : Plugin<*>> apply(type: Class<T>): T

    /**
     * Returns true if the container has a plugin with the given id, false otherwise.
     *
     * @param id The id of the plugin
     */
    fun hasPlugin(id: String): Boolean

    /**
     * Returns true if the container has a plugin with the given type, false otherwise.
     *
     * @param type The type of the plugin
     */
    fun hasPlugin(type: Class<out Plugin<*>>): Boolean

    /**
     * Returns the plugin for the given id.
     *
     * @param id The id of the plugin
     * @return the plugin or null if no plugin for the given id exists.
     */

    fun findPlugin(id: String): Plugin<*>?

    /**
     * Returns the plugin for the given type.
     *
     * @param type The type of the plugin
     * @return the plugin or null if no plugin for the given type exists.
     */

    fun <T : Plugin<*>> findPlugin(type: Class<T>): T

    /**
     * Returns a plugin with the specified id if this plugin has been used in the project.
     *
     * @param id The id of the plugin
     * @throws UnknownPluginException When there is no plugin with the given id.
     */
    fun getPlugin(id: String): Plugin<*>

    /**
     * Returns a plugin with the specified type if this plugin has been used in the project.
     *
     * @param type The type of the plugin
     * @throws UnknownPluginException When there is no plugin with the given type.
     */

    fun <T : Plugin<*>> getPlugin(type: Class<T>): T

    /**
     * Returns a plugin with the specified id if this plugin has been used in the project. You can use the Groovy
     * `[]` operator to call this method from a build script.
     *
     * @param id The id of the plugin
     * @throws UnknownPluginException When there is no plugin with the given id.
     */
    fun getAt(id: String): Plugin<*>

    /**
     * Returns a plugin with the specified type if this plugin has been used in the project. You can use the Groovy
     * `[]` operator to call this method from a build script.
     *
     * @param type The type of the plugin
     * @throws UnknownPluginException When there is no plugin with the given type.
     */

    fun <T : Plugin<*>> getAt(type: Class<T>): T

    /**
     * Executes or registers an action for a plugin with given id.
     * If the plugin was already applied, the action is executed.
     * If the plugin is applied sometime later the action will be executed after the plugin is applied.
     * If the plugin is never applied, the action is never executed.
     * The behavior is similar to [.withType].
     *
     * @param pluginId the id of the plugin
     * @param action the action
     */
    fun withId(pluginId: String, action: Action<in Plugin<*>>)
}
