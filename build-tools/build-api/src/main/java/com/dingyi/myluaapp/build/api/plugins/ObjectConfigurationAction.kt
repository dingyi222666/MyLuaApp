package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Plugin


/**
 *
 * An `ObjectConfigurationAction` allows you to apply [com.dingyi.myluaapp.build.api.Plugin]s and scripts to an object
 * or objects.
 */
interface ObjectConfigurationAction {
    /**
     *
     * Specifies some target objects to be configured. Any collections or arrays in the given parameters will be
     * flattened, and the script applied to each object in the result, in the order given. Each call to this method adds
     * some additional target objects.
     *
     * @param targets The target objects.
     * @return this
     */
    fun to(vararg targets: Any?): ObjectConfigurationAction

    /**
     * Adds a script to use to configure the target objects. You can call this method multiple times, to use multiple
     * scripts. Scripts and plugins are applied in the order that they are added.
     *
     * @param script The script. Evaluated as per [com.dingyi.myluaapp.build.api.Project.file]. However, note that
     * a URL can also be used, allowing the script to be fetched using HTTP, for example.
     * @return this
     */
    fun from(script: Any?): ObjectConfigurationAction

    /**
     * Adds a [com.dingyi.myluaapp.build.api.Plugin] to use to configure the target objects. You can call this method multiple
     * times, to use multiple plugins. Scripts and plugins are applied in the order that they are added.
     *
     * @param pluginClass The plugin to apply.
     * @return this
     */
    fun plugin(pluginClass: Class<in Plugin<*>>): ObjectConfigurationAction

    /**
     * Adds the plugin implemented by the given class to the target.
     *
     *
     * The class is expected to either implement [Plugin], or extend [org.gradle.model.RuleSource].
     * An exception will be thrown if the class is not a valid plugin implementation.
     *
     * @param pluginClass the plugin to apply
     * @return this
     */
    fun type(pluginClass: Class<*>): ObjectConfigurationAction

    /**
     * Adds a [org.gradle.api.Plugin] to use to configure the target objects. You can call this method multiple
     * times, to use multiple plugins. Scripts and plugins are applied in the order that they are added.
     *
     * @param pluginId The id of the plugin to apply.
     * @return this
     */
    fun plugin(pluginId: String): ObjectConfigurationAction
}
