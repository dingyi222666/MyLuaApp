package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.internal.metaobject.DynamicObject
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.Project

/**
 * A [Convention] manages a set of **convention objects**. When you add a convention object to a [Convention],
 * , and the properties and methods of the convention object become available as properties and methods of
 * the object which the convention is associated to. A convention object is simply a POJO Usually, a [Convention]
 * is used by plugins to extend a [Project] or a [Task].
 */
interface Convention:ExtensionContainer {

    /**
     * Returns the plugin convention objects contained in this convention.
     *
     * @return The plugins. Returns an empty map when this convention does not contain any convention objects.
     */
    fun getPlugins(): Map<String, Any?>

    /**
     * Locates the plugin convention object with the given type.
     *
     * @param type The convention object type.
     * @return The object. Never returns null.
     * @throws IllegalStateException When there is no such object contained in this convention, or when there are
     * multiple such objects.
     */
    @Throws(IllegalStateException::class)
    fun <T> getPlugin(type: Class<T>?): T

    /**
     * Locates the plugin convention object with the given type.
     *
     * @param type The convention object type.
     * @return The object. Returns null if there is no such object.
     * @throws IllegalStateException When there are multiple matching objects.
     */
    @Throws(IllegalStateException::class)
    fun <T> findPlugin(type: Class<T>): T

    /**
     * Returns a dynamic object which represents the properties and methods contributed by the extensions and convention objects contained in this
     * convention.
     *
     * @return The dynamic object
     */
    fun getExtensionsAsDynamicObject(): DynamicObject
}