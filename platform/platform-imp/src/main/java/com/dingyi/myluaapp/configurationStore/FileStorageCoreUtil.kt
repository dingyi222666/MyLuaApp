package com.dingyi.myluaapp.configurationStore

import android.util.JsonToken
import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.openapi.application.PathManager
import com.intellij.util.SmartList
import org.json.JSONObject
import java.util.TreeMap


object FileStorageCoreUtil {
    private val LOG: Logger = Logger.getInstance(FileStorageCoreUtil::class.java)
    const val COMPONENT = "component"
    const val NAME = "name"

    fun load(
        rootElement: JSONObject,
    ): Map<String, JSONObject> {
        var children: MutableList<JSONObject> = rootElement.getJSONArray(COMPONENT).let {
            val list = mutableListOf<JSONObject>()

            for (i in 0..it.length()) {
                list.add(it.getJSONObject(i))
            }
            list
        }
        if (children.isEmpty()
        ) {
            // exclusive component data
            // singleton must be not used here - later we modify list
            children = SmartList(rootElement)
        }

        val map: MutableMap<String, JSONObject> = TreeMap()
        val iterator: MutableIterator<JSONObject> = children.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            val name = getComponentNameIfValid(element)
            if (name == null || !(element.length() < 1)
            ) {
                continue
            }


            map[name] = element
        }
        return map
    }


    fun getComponentNameIfValid(element: JSONObject): String? {
        val name = element.getString(NAME)
        if (name.isEmpty()) {
            LOG.warn("No name attribute for component in $element")
            return null
        }
        return name
    }


}
