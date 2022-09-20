package com.dingyi.myluaapp.openapi.actions.internal

import android.util.ArrayMap
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.dingyi.myluaapp.openapi.actions.ActionManager
import com.dingyi.myluaapp.openapi.actions.AnAction
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap


class DefaultActionManager : ActionManager {

    private val myLock = Any()
    private val idToAction: MutableMap<String, AnAction> = LinkedHashMap()

    private val idToIndex = ArrayMap<String, Int>()
    private val actionToId = HashMap<Any, String>()
    private val idToGroupId = HashMap<String, String>()

    private var myRegisteredActionsCount = 0

    override fun getAction(actionId: String): AnAction? {
        synchronized(myLock) {
            return idToAction[actionId];
        }
    }

    override fun getId(action: AnAction): String? {
        synchronized(myLock) {
            return actionToId.get(action)
        }
    }

    override fun registerAction(actionId: String, action: AnAction) {
        idToIndex.put(actionId, myRegisteredActionsCount++);
        actionToId.put(action, actionId);
    }

    /* override fun registerAction(actionId: String, action: AnAction, pluginId: Any) {
         TODO("Not yet implemented")
     }
 */
    override fun unregisterAction(actionId: String) {
        val actionToRemove = idToAction.remove(actionId)
        if (actionToRemove != null) {
            actionToId.remove(actionToRemove)
            idToIndex.remove(actionId)
        }
    }

    override fun replaceAction(actionId: String, newAction: AnAction) {
        val oldAction = getAction(actionId)
        val oldIndex = idToIndex.getOrDefault(actionId, -1) // Valid indices >= 0

        if (oldAction != null) {

            unregisterAction(actionId)
        }
        registerAction(actionId, newAction)
        if (oldIndex >= 0) {
            idToIndex.put(actionId, oldIndex)
        }
        //return oldAction
    }

    override fun getActionIdList(idPrefix: String): List<String> {
        val result = mutableListOf<String>()
        synchronized(myLock) {
            for (id in idToAction.keys) {
                if (id.startsWith(idPrefix)) {
                    result.add(id)
                }
            }
        }
        return result
    }

    override fun isGroup(actionId: String): Boolean {
        return getAction(actionId) is ActionGroup
    }


}