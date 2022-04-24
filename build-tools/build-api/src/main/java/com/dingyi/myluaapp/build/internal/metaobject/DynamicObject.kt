package com.dingyi.myluaapp.build.internal.metaobject

interface DynamicObject:MethodAccess,PropertyAccess {

    /**
     * Don't use this method. Use the overload [PropertyAccess.tryGetProperty] instead.
     */
    @Throws(RuntimeException::class)
    fun getProperty(name: String): Any?

    /**
     * Don't use this method. Use the overload [PropertyAccess.trySetProperty] instead.
     */
    @Throws(RuntimeException::class)
    fun setProperty(name: String, value: Any?)

    /**
     * Don't use this method. Use the overload [MethodAccess.tryInvokeMethod] instead.
     */
    @Throws(RuntimeException::class)
    fun invokeMethod(name: String, vararg arguments: Any?): Any?
}