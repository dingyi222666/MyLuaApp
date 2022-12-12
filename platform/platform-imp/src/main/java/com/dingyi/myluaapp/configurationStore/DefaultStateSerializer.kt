package com.dingyi.myluaapp.configurationStore



import org.json.JSONObject


fun <T> deserializeState(stateElement: JSONObject?, stateClass: Class<T>, mergeInto: T?): T? {
    @Suppress("DEPRECATION", "UNCHECKED_CAST")
    return when {
        stateElement == null -> mergeInto
        stateClass == JSONObject::class.java -> stateElement as T?
        //mergeInto == null -> jdomSerializer.deserialize(stateElement, stateClass)
        else -> {
            /*stateElement.*/deserializeInto(stateElement,mergeInto)
            mergeInto
        }
    }
}


private fun deserializeInto(stateElement: JSONObject?,mergeInto: Any?) {
 //TODO: deserializeInto
}