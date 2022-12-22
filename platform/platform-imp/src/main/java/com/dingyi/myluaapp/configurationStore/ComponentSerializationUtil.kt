package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.openapi.components.PersistentStateComponent
import com.dingyi.myluaapp.util.JSONSerializer
import com.intellij.util.ReflectionUtil
import org.json.JSONObject
import java.lang.reflect.TypeVariable


object ComponentSerializationUtil {

    fun <S> getStateClass(aClass: Class<out PersistentStateComponent<*>>): Class<S> {
        val variable: TypeVariable<Class<PersistentStateComponent<*>>> =
            PersistentStateComponent::class.java.typeParameters[0]
        val type = ReflectionUtil.resolveVariableInHierarchy(variable, aClass) ?: error(
            aClass
        )
        val result = ReflectionUtil.getRawType(type) as Class<S>
        return if (result == Any::class.java) {
            aClass as Class<S>
        } else result
    }

    fun <S : Any> loadComponentState(
        configuration: PersistentStateComponent<S>,
        element: JSONObject?
    ) {
        if (element != null) {
            val stateClass = getStateClass<S>(configuration.javaClass)
            val state =
              /*  if (stateClass == JSONObject::class.java) element else*/ JSONSerializer.deserialize(
                    element,
                    stateClass
                )
            configuration.loadState(state)
        }
    }
}
