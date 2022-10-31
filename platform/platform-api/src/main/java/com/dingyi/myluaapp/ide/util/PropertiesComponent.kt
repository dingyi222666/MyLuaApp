package com.dingyi.myluaapp.ide.util

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.util.SimpleModificationTracker
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.text.StringUtilRt


/**
 * Allows simple persistence of application/project-level values.
 *
 *
 * Roaming is disabled for PropertiesComponent, so, use it only and only for temporary non-roamable properties.
 *
 *
 * See [Using PropertiesComponent for Simple non-roamable Persistence](http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html).
 *
 * @author max
 * @author Konstantin Bulenkov
 */
abstract class PropertiesComponent : SimpleModificationTracker() {
    abstract fun unsetValue(name: String)
    abstract fun isValueSet(name: String): Boolean

    abstract fun getValue(name: String): String?

    /**
     * Consider to use [.setValue] to avoid write defaults.
     */
    abstract fun setValue(name: String, value: String?)

    /**
     * Set value or unset if equals to default value
     */
    abstract fun setValue(
        name: String,
        value: String?,
        defaultValue: String?
    )

    /**
     * Set value or unset if equals to default value
     */
    abstract fun setValue(name: String, value: Float, defaultValue: Float)

    /**
     * Set value or unset if equals to default value
     */
    abstract fun setValue(name: String, value: Int, defaultValue: Int)

    /**
     * Set value or unset if equals to false
     */
    fun setValue(name: String, value: Boolean) {
        setValue(name, value, false)
    }

    /**
     * Set value or unset if equals to default
     */
    abstract fun setValue(name: String, value: Boolean, defaultValue: Boolean)
    abstract fun getValues(name: String): Array<String>?
    abstract fun setValues(name: String?, values: Array<String>)

    fun isTrueValue(name: String): Boolean {
        return java.lang.Boolean.parseBoolean(getValue(name))
    }

    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        return if (isValueSet(name)) isTrueValue(name) else defaultValue
    }

    fun getBoolean(name: String): Boolean {
        return getBoolean(name, false)
    }


    fun getValue(name: String, defaultValue: String): @NlsSafe String {
        val value = getValue(name)
        return value ?: defaultValue
    }


    fun getInt(name: String, defaultValue: Int): Int {
        return StringUtilRt.parseInt(getValue(name), defaultValue)
    }

    fun getLong(name: String, defaultValue: Long): Long {
        return StringUtilRt.parseLong(getValue(name), defaultValue)
    }


    fun saveFields(`object`: Any): Boolean {
        return try {
            for (field in `object`.javaClass.declaredFields) {
                field.isAccessible = true
                val annotation: PropertyName? = field.getAnnotation(PropertyName::class.java)
                if (annotation != null) {
                    val name = annotation.value
                    setValue(name, java.lang.String.valueOf(field[`object`]))
                }
            }
            true
        } catch (e: IllegalAccessException) {
            false
        }
    }

    fun loadFields(field: Any): Boolean {
        return try {
            for (field in field.javaClass.declaredFields) {
                field.isAccessible = true
                val annotation: PropertyName? = field.getAnnotation(PropertyName::class.java)
                if (annotation != null) {
                    val type = field.type
                    var defaultValue = annotation.defaultValue
                    if (PropertyName.NOT_SET == defaultValue) {

                        when (type) {
                            Boolean::class.javaPrimitiveType -> defaultValue =
                                java.lang.String.valueOf(field.getBoolean(field))

                            Long::class.javaPrimitiveType -> defaultValue =
                                java.lang.String.valueOf(field.getLong(field))

                            Int::class.javaPrimitiveType -> java.lang.String.valueOf(field.getInt(field))

                            Short::class.javaPrimitiveType ->
                                defaultValue = java.lang.String.valueOf(field.getShort(field))

                            Byte::class.javaPrimitiveType ->
                                defaultValue = java.lang.String.valueOf(field.getByte(field))

                            Double::class.javaPrimitiveType ->
                                defaultValue = java.lang.String.valueOf(field.getDouble(field))

                            Float::class.javaPrimitiveType ->
                                defaultValue = java.lang.String.valueOf(field.getFloat(field))

                            String::class.java ->
                                defaultValue = java.lang.String.valueOf(field[field])

                        }


                    }
                    val stringValue = getValue(annotation.value, defaultValue)
                    val value = when (type) {
                        Boolean::class.javaPrimitiveType -> java.lang.Boolean.valueOf(stringValue)
                        Long::class.javaPrimitiveType -> stringValue.toLong()
                        Int::class.javaPrimitiveType -> stringValue.toInt()
                        Short::class.javaPrimitiveType -> stringValue.toShort()
                        Byte::class.javaPrimitiveType -> stringValue.toByte()
                        Double::class.javaPrimitiveType -> stringValue.toDouble()
                        Float::class.javaPrimitiveType -> stringValue.toFloat()
                        String::class.java -> stringValue
                        else -> null
                    }


                    if (value != null) {
                        field[field] = value
                    }
                }
            }
            true
        } catch (e: IllegalAccessException) {
            false
        }
    }

    fun getFloat(name: String, defaultValue: Float): Float {
        if (isValueSet(name)) {
            try {
                val value = getValue(name)
                if (value != null) return value.toFloat()
            } catch (ignore: NumberFormatException) {
            }
        }
        return defaultValue
    }

    companion object {
        /**
         * Returns the project-level instance.
         */
        fun getInstance(project: Project): PropertiesComponent {
            return project[PropertiesComponent::class.java]
        }

        /**
         * Returns the application-level instance.
         */
        val instance: PropertiesComponent
            get() = ApplicationManager.getApplication().get(PropertiesComponent::class.java)
    }
}
