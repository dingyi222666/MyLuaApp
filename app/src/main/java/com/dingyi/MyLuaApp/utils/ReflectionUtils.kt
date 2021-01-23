package com.dingyi.MyLuaApp.utils


fun getPrivateField(objects: Any, fieldName: String): Any? {
    runCatching {
        val field = objects.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(objects)
    }.onFailure {
        e(it.message.toString())
        return null
    }
    return null
}

