package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Action


interface PluginAware {

    /**
     * The container of plugins that have been applied to this object.
     *
     *
     * While not deprecated, it is preferred to use the methods of this interface or the [plugin manager][.getPluginManager] than use the plugin container.
     *
     *
     * Use one of the 'apply' methods on this interface or on the [plugin manager][.getPluginManager] to apply plugins instead of applying via the plugin container.
     *
     *
     * Use [PluginManager.hasPlugin] or similar to query for the application of plugins instead of doing so via the plugin container.
     *
     * @return the plugin container
     * @see .apply
     *
     * @see PluginManager.hasPlugin
     */
    fun getPlugins(): PluginContainer



    /**
     * Applies zero or more plugins or scripts.
     *
     *
     * The given closure is used to configure an [ObjectConfigurationAction], which “builds” the plugin application.
     *
     *
     * This method differs from [.apply] in that it allows methods of the configuration action to be invoked more than once.
     *
     * @param action the action to configure an [ObjectConfigurationAction] with before “executing” it
     * @see .apply
     */
    fun apply(action: Action<in ObjectConfigurationAction>)

    /**
     * Applies a plugin or script, using the given options provided as a map. Does nothing if the plugin has already been applied.
     *
     *
     * The given map is applied as a series of method calls to a newly created [ObjectConfigurationAction].
     * That is, each key in the map is expected to be the name of a method [ObjectConfigurationAction] and the value to be compatible arguments to that method.
     *
     *
     * The following options are available:
     *
     *  * `from`: A script to apply. Accepts any path supported by [org.gradle.api.Project.uri].
     *
     *  * `plugin`: The id or implementation class of the plugin to apply.
     *
     *  * `to`: The target delegate object or objects. The default is this plugin aware object. Use this to configure objects other than this object.
     *
     * @param options the options to use to configure and [ObjectConfigurationAction] before “executing” it
     */
    fun apply(options: Map<String?, *>)

    /**
     * The plugin manager for this plugin aware object.
     *
     * @return the plugin manager
     * @since 2.3
     */
    fun getPluginManager(): PluginManager
}
