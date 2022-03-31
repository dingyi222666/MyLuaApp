package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Plugin

interface PluginContainer {

    fun apply(id: String): Plugin<*>

    /**
     * Applies a plugin to the project. This usually means that the plugin uses the project API to add and modify the
     * state of the project. This method can be called an arbitrary number of times for a particular plugin type. The
     * plugin will be actually used only the first time this method is called.
     *
     * @param type The type of the plugin to be used
     * @return The plugin which has been used against the project.
     */
    fun <T> apply(type: Class<T>): T

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
    fun <T> findPlugin(type: Class<T>): T

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
   
    fun <T> getPlugin(type: Class<T>): T


}