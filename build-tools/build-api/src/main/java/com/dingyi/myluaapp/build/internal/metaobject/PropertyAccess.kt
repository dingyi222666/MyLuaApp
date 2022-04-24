package com.dingyi.myluaapp.build.internal.metaobject


/**
 * Provides dynamic access to properties of some object.
 */
interface PropertyAccess {
    /**
     * Returns true when this object is known to have the given property.
     *
     *
     * Note that not every property is known. Some properties require an attempt to get or set their value before they are discovered.
     */
    fun hasProperty(name: String): Boolean

    /**
     * Gets the value of the given property, if present.
     */
    fun tryGetProperty(name: String):DynamicInvokeResult

    /**
     * Sets the value of the given property, if present.
     *
     * @return true if the property was found
     */
    fun trySetProperty(
        name: String,
        value: Any?
    ): DynamicInvokeResult

    /**
     * Returns the properties known for this object.
     */
    fun getProperties(): Map<String, *>
}