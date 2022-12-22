package com.dingyi.myluaapp.util

import com.dingyi.myluaapp.common.ktx.checkNotNull
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Field

object JSONSerializer {

    fun serialize(any: Any): JSONObject {
        val json = JSONObject()
        val clazz = any::class.java
        val fields = clazz.declaredFields
        for (field in fields) {
            field.isAccessible = true
            val name = field.name
            val value = field.get(any)
            json.put(name, value)
        }
        return json
    }


    // deserialize jsonObject to new object
    fun <T : Any> deserialize(
        jsonObject: JSONObject,
        targetClass: Class<T>,
        lastObject: T? = null
    ): T {
        val targetObject = lastObject ?: targetClass.newInstance()

        val declaredFields = targetClass.declaredFields

        for (key in jsonObject.keys()) {
            val field = declaredFields.find { it.name.lowercase() == key.lowercase() }?.apply {
                isAccessible = true
            }.checkNotNull()

            when (field.type) {
                Int::class.java ->
                    field.set(targetObject, jsonObject.getInt(key))

                Double::class.java ->
                    field.set(targetObject, jsonObject.getDouble(key))

                String::class.java -> field.set(targetObject, jsonObject.getString(key))

                Array::class.java -> field.set(
                    targetObject,
                    deserializeArray(jsonObject.getJSONArray(key), field)
                )

                Float::class.java -> field.set(targetObject, jsonObject.getDouble(key).toFloat())

                Long::class.java -> field.set(targetObject, jsonObject.getLong(key))

                else -> deserialize(jsonObject.getJSONObject(key), targetClass, targetObject)

            }

        }

        return targetObject

    }

    private fun deserializeArray(jsonArray: JSONArray, field: Field): Array<*> {
        TODO("deserializeArray")
    }

    inline fun <reified T : Any> deserialize(
        jsonObject: JSONObject,
        lastObject: T? = null
    ): T = deserialize(jsonObject, T::class.java, lastObject)


}
