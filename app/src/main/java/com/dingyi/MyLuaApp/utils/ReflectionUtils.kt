@file:JvmName("ReflectionUtils")
package com.dingyi.MyLuaApp.utils

import kotlin.reflect.KClass
import kotlin.reflect.KType


fun getPrivateField(objects: Any, fieldName: String): Any? {
    runCatching {
        val field = objects.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(objects)
    }.onFailure {
        printError(it.message.toString())
        return null
    }
    return null
}

fun getPrivateField(clazz: Class<*>,objects: Any, fieldName: String): Any? {
    runCatching {
        val field = clazz.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(objects)
    }.onFailure {
        printError(it.message.toString())
        return null
    }
    return null
}

fun getPrivateMethod(objects: Any,methodName:String,methodArg:Array<Class<*>>,methodRunArg:Array<Any>?):Any? {
    runCatching {
        val field = objects.javaClass.getDeclaredMethod(methodName, *methodArg)
        field.isAccessible = true
        methodRunArg?.let {
           return field.invoke(objects,*it)
        }
        return field.invoke(objects,null)
    }.onFailure {
        printError(it.message.toString())
        return null
    }
    return null
}


fun KType.isClass(cls: KClass<*>): Boolean {
    return this.classifier == cls
}

val KType.isTypeString: Boolean get() = this.isClass(String::class)
val KType.isTypeInt: Boolean get() = this.isClass(Int::class) || this.isClass(java.lang.Integer::class)
val KType.isTypeLong: Boolean get() = this.isClass(Long::class) || this.isClass(java.lang.Long::class)
val KType.isTypeByte: Boolean get() = this.isClass(Byte::class) || this.isClass(java.lang.Byte::class)
val KType.isTypeShort: Boolean get() = this.isClass(Short::class) || this.isClass(java.lang.Short::class)
val KType.isTypeChar: Boolean get() = this.isClass(Char::class) || this.isClass(java.lang.Character::class)
val KType.isTypeBoolean: Boolean get() = this.isClass(Boolean::class) || this.isClass(java.lang.Boolean::class)
val KType.isTypeFloat: Boolean get() = this.isClass(Float::class) || this.isClass(java.lang.Float::class)
val KType.isTypeDouble: Boolean get() = this.isClass(Double::class) || this.isClass(java.lang.Double::class)
val KType.isTypeByteArray: Boolean get() = this.isClass(ByteArray::class)

