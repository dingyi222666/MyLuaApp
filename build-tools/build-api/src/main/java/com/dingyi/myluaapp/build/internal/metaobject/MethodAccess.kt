package com.dingyi.myluaapp.build.internal.metaobject

/**
 * Provides dynamic access to methods of some object.
 */
interface MethodAccess {

    /**
     * Returns true when this object is known to have a method with the given name that accepts the given arguments.
     *
     *
     * Note that not every method is known. Some methods may require an attempt invoke it in order for them to be discovered.
     */
    fun hasMethod(name: String, vararg arguments: Any?): Boolean

    /**
     * Invokes the method with the given name and arguments.
     */
    fun tryInvokeMethod(
        name: String,
        vararg arguments: Any?
    ): DynamicInvokeResult
}