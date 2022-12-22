package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.util.JSONSerializer
import com.intellij.openapi.util.WriteExternalException
import org.json.JSONObject


abstract class SaveSessionBase :
    SaveSessionProducer/*, SafeWriteRequestor, LargeFileWriteRequestor*/ {
    final override fun setState(component: Any?, componentName: String, state: Any?) {
        if (state == null) {
            setSerializedState(componentName, null)
            return
        }

        val element: JSONObject?
        try {
            element = serializeState(state)
        } catch (e: WriteExternalException) {
            LOG.debug(e)
            return
        } catch (e: Throwable) {
            LOG.error("Unable to serialize $componentName state", e)
            return
        }

        setSerializedState(componentName, element)
    }

    abstract fun setSerializedState(componentName: String, element: JSONObject?)
}

internal fun serializeState(state: Any): JSONObject? {
    @Suppress("DEPRECATION")
    when (state) {
        is JSONObject -> return state
        else -> return JSONSerializer.serialize(state)
    }
}

