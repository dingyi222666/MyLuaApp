package com.dingyi.editor.language.java.api

import com.androlua.LuaApplication
import com.dingyi.lua.analyze.info.Type
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*


/**
 * @author: dingyi
 * @date: 2021/8/20 23:15
 * @description:
 **/
object SystemApiHelper {

    fun findClass(className: String): Boolean {
        return runCatching {
            LuaApplication.getInstance().classLoader.loadClass(className)
        }.isSuccess
    }

    private fun findPublicStaticField(clazz: Class<*>, name: String): List<Field> {
        return mutableListOf<Field>().apply {
            clazz
                .fields
                .filter {
                    Modifier.isStatic(it.modifiers)
                }
                .filter {
                    it.name.lowercase(Locale.getDefault()).startsWith(name)
                }
                .forEach {
                    add(it)
                }
        }
    }

    private fun findPublicStaticMethod(clazz: Class<*>, name: String): List<Method> {
        return mutableListOf<Method>().apply {
            clazz
                .methods
                .filter {
                    Modifier.isStatic(it.modifiers)
                }
                .filter {
                    it.name.lowercase(Locale.getDefault()).startsWith(name)
                }
                .forEach {
                    add(it)
                }
        }
    }

    private fun findPublicMethod(clazz: Class<*>, name: String): List<Method> {
        return mutableListOf<Method>().apply {
            clazz
                .methods
                .filterNot {
                    Modifier.isStrict(it.modifiers)
                }
                .filter {
                    it.name.lowercase(Locale.getDefault()).startsWith(name.lowercase(Locale.getDefault()))
                }
                .forEach {
                    add(it)
                }
        }
    }

    private fun findPublicField(clazz: Class<*>, name: String): List<Field> {
        return mutableListOf<Field>().apply {
            clazz
                .fields
                .filterNot {
                    Modifier.isStrict(it.modifiers)
                }
                .filter {
                    it.name.lowercase(Locale.getDefault()).startsWith(name.lowercase(Locale.getDefault()))
                }
                .forEach {
                    add(it)
                }
        }
    }

    private fun getPublicStaticField(clazz: Class<*>, name: String): Class<*>? {
        return runCatching {
            clazz.getField(name)
        }.getOrNull()?.run {
            if (Modifier.isStatic(this.modifiers)) {
                this.type
            }
            null
        }
    }

    private fun getPublicStaticMethod(clazz: Class<*>, name: String): Class<*>? {
        return runCatching {
            clazz.getMethod(name)
        }.getOrNull()?.run {
            if (Modifier.isStatic(this.modifiers)) {
                this.returnType
            }
            null
        }
    }

    fun getPublicStaticFieldData(clazz: Class<*>, name: String): List<FieldData> {
        return findPublicStaticMethod(clazz, name)
            .map {
                FieldData(Type.METHOD, it.name, it.returnType,it)
            }
            .plus(
                findPublicStaticField(clazz, name).map {
                    FieldData(Type.FIELD, it.name, it.type,it)
                }
            )
    }

    fun getPublicFieldData(clazz: Class<*>, name: String): List<FieldData> {
        return findPublicMethod(clazz, name)
            .map {
                FieldData(Type.METHOD, it.name, it.returnType,it)
            }
            .plus(
                findPublicField(clazz, name).map {
                    FieldData(Type.FIELD, it.name, it.type,it)
                }
            )
    }

    data class ClassData(
        var classList: List<Class<*>>,
        val name: String,
        var isNewInstance: Boolean = false,
    )

    data class FieldData(
        val type: Type,
        val name: String = "",
        val typeClass: Class<*>?,
        val base:AccessibleObject
    )

    fun analyzeCode(code: String): ClassData {
        var data = ClassData(listOf(), code)
        code.split('.').forEach {
            data = analyzeCode(it, data)
        }
        return data
    }

    private fun analyzeCode(
        code: String,
        last: ClassData
    ): ClassData {
        val (lastClassList) = last
        val resultList = mutableListOf<Class<*>>()
        if (lastClassList.isEmpty()) {
            AndroidApi
                .findClassesByEnd(code)
                .map {
                    getClass(it)
                }
                .forEach {
                    it?.let { javaClass -> resultList.add(javaClass) }
                }
        } else {
            last.classList.forEach { lastClass ->
                arrayOf(
                    getPublicStaticField(lastClass, code),
                    getPublicStaticMethod(lastClass, code)
                ).forEach {
                    it?.let { javaClass -> resultList.add(javaClass) }
                }
            }
        }

        if (resultList.isNotEmpty()) {
            last.classList = resultList
        }
        last.isNewInstance = code.indexOf("(") != -1
        return last
    }

    private fun getClass(it: String): Class<*>? {
        return runCatching {
            LuaApplication.getInstance().classLoader.loadClass(it)
        }.getOrNull()
    }

}