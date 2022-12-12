package com.dingyi.myluaapp.openapi.actions.internal

import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.ide.plugins.PluginDescriptorImpl
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.dingyi.myluaapp.openapi.actions.ActionGroupStub
import com.dingyi.myluaapp.openapi.actions.ActionManager
import com.dingyi.myluaapp.openapi.actions.ActionStubBase
import com.dingyi.myluaapp.openapi.actions.AnAction
import com.dingyi.myluaapp.openapi.actions.DefaultActionGroup
import com.dingyi.myluaapp.openapi.actions.IdeActions
import com.dingyi.myluaapp.openapi.actions.Presentation
import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.dsl.plugin.actions.ActionDslBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.actions.ActionGroupDslBuilder
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.util.text.StringUtilRt
import com.intellij.openapi.util.text.Strings
import com.intellij.util.containers.MultiMap
import java.util.function.Supplier


class ActionManagerImpl : ActionManager {

    private val myLock = Any()
    private val idToAction: MutableMap<String, AnAction> = LinkedHashMap()

    private val idToIndex = HashMap<String, Int>()
    private val actionToId = HashMap<Any, String>()
    private val idToGroupId = MultiMap<String, String>()
    private val pluginToId = MultiMap<PluginId, String>()


    private var myRegisteredActionsCount = 0

    override fun getAction(actionId: String): AnAction? {
        return getActionImpl(actionId, false)
    }

    private fun getActionOrStub(id: String): AnAction? {
        return getActionImpl(id, true)
    }

    private fun getActionImpl(id: String, canReturnStub: Boolean): AnAction? {
        var action: AnAction?
        synchronized(myLock) {
            action = idToAction[id]
            if (canReturnStub || action !is ActionStubBase) {
                return action
            }
        }
        val converted: AnAction? =
            if (action is ActionStub) convertStub(action as ActionStub) else convertGroupStub(
                action as ActionGroupStub,
                this
            )
        if (converted == null) {
            unregisterAction(id)
            return null
        }

        synchronized(myLock) {
            action = idToAction[id]
            if (action is ActionStubBase) {
                action = replaceStub(action as ActionStubBase, converted)
            }
            return action
        }
    }


    private fun replaceStub(stub: ActionStubBase, anAction: AnAction): AnAction {
        synchronized(myLock) {
            LOG.assertTrue(actionToId.containsKey(stub))
            actionToId.remove(stub)
            LOG.assertTrue(idToAction.containsKey(stub.id))
            val action = idToAction.remove(stub.id)
            LOG.assertTrue(action != null)
            LOG.assertTrue(action == stub)
            actionToId[anAction] = stub.id
            idToAction.put(stub.id, anAction);

        }
        return anAction
    }

    override fun getId(action: AnAction): String? {
        synchronized(myLock) {
            return actionToId[action]
        }
    }


    override fun registerAction(actionId: String, action: AnAction) {
        synchronized(myLock) {
            idToIndex[actionId] = myRegisteredActionsCount++;
            actionToId[action] = actionId;
            idToAction[actionId] = action
        }
    }

    fun registerPluginAction(descriptor: PluginDescriptorImpl) {

        descriptor.actions?.let {
            registerActionList(it.allActions, descriptor)
            registerActionList(it.allActionGroups, descriptor)
        }

    }


    private fun registerActionList(targetList: List<Any>, plugin: PluginDescriptorImpl) {
        targetList.forEach {
            when (it) {
                is ActionDslBuilder -> processActionElement(it, plugin)
                is ActionGroupDslBuilder -> processGroupElement(it, plugin)
            }
        }
    }


    private fun processGroupElement(
        element: ActionGroupDslBuilder,
        plugin: PluginDescriptorImpl,
    ): AnAction? {
        val className: String = element.targetClass.toString()

        return try {
            val id = element.id
            if (id.isEmpty()) {
                LOG.error("ID of the group ${plugin.id} cannot be an empty string")
                return null
            }

            val group: ActionGroup
            var customClass = false
            if (DefaultActionGroup::class.java.name == className) {
                group = DefaultActionGroup()
            } /*else if (DefaultCompactActionGroup::class.java.getName() == className) {
                group = DefaultCompactActionGroup()
            } */ else if (id == null) {
                val obj = ApplicationManager.getApplication().instantiateClass(className, plugin)
                if (obj !is ActionGroup) {
                    LOG.error(
                        "class with name \"" + className + "\" should be instance of " + ActionGroup::class.java.name
                    )
                    return null
                }
                /* if (element.children.size() !== element.count(ADD_TO_GROUP_ELEMENT_NAME)) {  //
                     if (obj !is DefaultActionGroup) {
                         reportActionError(
                             plugin.getPluginId(),
                             "class with name \"" + className + "\" should be instance of " + DefaultActionGroup::class.java.name +
                                     " because there are children specified"
                         )
                         return null
                     }
                 }*/
                customClass = true
                group = obj
            } else {
                group = ActionGroupStub(id, className, plugin)
                customClass = true
            }
            /*// read ID and register loaded group
            if (java.lang.Boolean.parseBoolean(element.attributes.get(INTERNAL_ATTR_NAME)) && !ApplicationManager.getApplication()
                    .isInternal()
            ) {
                myNotRegisteredInternalActionIds.add(id)
                return null
            }
            if (id == null) {
                id = "<anonymous-group-" + myAnonymousGroupIdCounter++ + ">"
            }*/
            this.registerAction(id, group, plugin.pluginId)

            val presentation = group.templatePresentation


            // text
            val text = element.text
            // don't override value which was set in API with empty value from xml descriptor
            if (!Strings.isEmpty(text) || presentation.getText() == null) {
                presentation.setText(text)
            }

            // description
            val description: String = element.description //NON-NLS
            val descriptionSupplier = Supplier<String?> { description }
            // don't override value which was set in API with empty value from xml descriptor
            if (!Strings.isEmpty(descriptionSupplier.get()) /*|| presentation.getDescription() == null*/) {
                presentation.setDescription(descriptionSupplier)
            }


            // icon
            val iconPath = element.iconPath
            if (group is ActionGroupStub) {
                group.iconPath = iconPath
            } else {
                updateIcon(group, iconPath)
            }

            // popup
            val popup = element.popup
            group.setPopup(popup)
            if (group is ActionGroupStub) {
                group.popupDefinedInXml = true
            }


            // process all group's children. There are other groups, actions, references and links
            registerActionList(element.allActions, plugin)
            registerActionList(element.allActionGroups, plugin)

            group
        } catch (e: Exception) {
            val message = "cannot create class \"$className\""
            LOG.error(message, e)
            null
        }
    }

    /**
     * @return instance of ActionGroup or ActionStub. The method never returns real subclasses of `AnAction`.
     */
    private fun processActionElement(
        element: ActionDslBuilder,
        plugin: PluginDescriptorImpl,
    ): AnAction {

        val className = element.targetClass.toString()


        // read ID and register loaded action
        val id: String = obtainActionId(element, className)

        val iconPath = element.iconPath

        val textValue = element.text
        val descriptionValue: String = element.description
        val stub = ActionStub(className, id, plugin, iconPath) {
            val presentation = Presentation()
            presentation.setText { textValue }

            presentation.setDescription { descriptionValue }

            presentation
        }

        element.allAddGroups.forEach {
            processAddToGroupNode(
                stub,
                /*element,*/
                plugin.pluginId,
                it,
                /* isSecondary(e)*/true
            )
        }

        this.registerAction(id, stub, plugin.pluginId)
        return stub
    }


    /**
     * @param element description of link
     */
    private fun processAddToGroupNode(
        action: AnAction,
        /*element: ActionDslBuilder,*/
        pluginId: PluginId,
        groupId: String,
        secondary: Boolean
    ) {
        val name = if (action is ActionStub) action.className else action.javaClass.name
        val id = if (action is ActionStub) action.id else actionToId[action]!!
        val actionName = "$name ($id)"

        // parent group
        val parentGroup: AnAction =
            getParentGroup(groupId, actionName, pluginId)
                ?: return


        addToGroupInner(parentGroup, action, secondary)
    }


    fun getParentGroup(
        groupId: String?,
        actionName: String?,
        pluginId: PluginId?
    ): DefaultActionGroup? {
        if (groupId.isNullOrEmpty()) {
            LOG.error("$actionName: attribute \"group-id\" should be defined")
            return null
        }
        var parentGroup = getActionImpl(groupId, true)
        if (parentGroup == null) {
            LOG.error(
                "$actionName: group with id \"$groupId\" isn't registered; action will be added to the \"Other\" group",
            )
            parentGroup = getActionImpl(IdeActions.GROUP_OTHER_MENU, true)
        }
        if (parentGroup !is DefaultActionGroup) {
            LOG.error(
                /* pluginId,*/
                actionName + ": group with id \"" + groupId + "\" should be instance of " + DefaultActionGroup::class.java.name +
                        " but was " + (parentGroup?.javaClass ?: "[null]")
            )
            return null
        }
        return parentGroup
    }

    private fun addToGroupInner(
        group: AnAction,
        action: AnAction,
        secondary: Boolean
    ) {
        val actionId = if (action is ActionStub) action.id else actionToId[action]!!
        (group as DefaultActionGroup).addAction(action, this).setAsSecondary(secondary)
        idToGroupId.putValue(actionId, actionToId[group])
    }


    private fun obtainActionId(element: ActionDslBuilder, className: String): String {
        val id = element.id ?: ""
        return if (Strings.isEmpty(id)) StringUtilRt.getShortName(className) else id
    }

    override fun registerAction(actionId: String, action: AnAction, pluginId: PluginId) {
        registerAction(actionId, action)
        synchronized(myLock) {
            pluginToId.putValue(pluginId, actionId)
        }
    }


    override fun unregisterAction(actionId: String) {
        unregisterAction(actionId, true)
    }

    private fun unregisterAction(actionId: String, removeFromGroups: Boolean = true) {
        synchronized(myLock) {
            val actionToRemove = idToAction.remove(actionId)
            if (actionToRemove != null) {
                actionToId.remove(actionToRemove)
                idToIndex.remove(actionId)
            }


            for ((_, value) in pluginToId.entrySet()) {
                value.remove(actionId)
            }

            if (removeFromGroups) {
                /*   val customActionSchema: CustomActionsSchema =
                       ApplicationManager.getApplication().getServiceIfCreated(
                           CustomActionsSchema::class.java
                       )*/
                for (groupId in idToGroupId[actionId]) {
                    /* if (customActionSchema != null) {
                         customActionSchema.invalidateCustomizedActionGroup(groupId)
                     }*/
                    val group = getActionOrStub(groupId) as DefaultActionGroup?
                    if (group == null) {
                        LOG.error("Trying to remove action $actionId from non-existing group $groupId")
                        continue
                    }
                    group.remove(actionToRemove!!, actionId)
                    if (group is ActionGroupStub) {
                        continue
                    }
                    //group can be used as a stub in other actions
                    for (parentOfGroup in idToGroupId[groupId]) {
                        val parentOfGroupAction =
                            getActionOrStub(parentOfGroup) as DefaultActionGroup?
                        if (parentOfGroupAction == null) {
                            LOG.error("Trying to remove action $actionId from non-existing group $parentOfGroup")
                            continue
                        }
                        for (stub in parentOfGroupAction.getChildActionsOrStubs()) {
                            if (stub is ActionGroupStub && stub.id === groupId) {
                                stub.remove(actionToRemove, actionId)
                            }
                        }

                    }
                }
            }
            if (actionToRemove is ActionGroup) {
                idToGroupId.entrySet().forEach { (_, value) ->
                    value.remove(actionId)
                }
            }

        }
    }

    override fun replaceAction(actionId: String, newAction: AnAction) {
        replaceActionImpl(actionId, newAction)
    }

    private fun replaceActionImpl(actionId: String, newAction: AnAction): AnAction? {

        val oldAction = getAction(actionId)
        val oldIndex = idToIndex.getOrDefault(actionId, -1) // Valid indices >= 0

        val isGroup = oldAction is ActionGroup
        check(isGroup == newAction is ActionGroup) { "cannot replace a group with an action and vice versa: $actionId" }
        for (groupId in idToGroupId[actionId]) {
            val group = getActionOrStub(groupId) as DefaultActionGroup?
                ?: throw IllegalStateException("Trying to replace action which has been added to a non-existing group $groupId")
            group.replaceAction(oldAction!!, newAction)
        }
        if (oldAction != null) {
            unregisterAction(actionId, false)
        }
        registerAction(actionId, newAction)
        synchronized(myLock) {
            if (oldIndex >= 0) {
                idToIndex[actionId] = oldIndex
            }
        }

        return oldAction
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


    companion object {
        private val LOG = Logger.getInstance(ActionManagerImpl::class.java)


        fun convertStub(stub: ActionStub): AnAction? {
            val anAction = instantiate(stub.className, stub.plugin, AnAction::class.java)
                ?: return null
            stub.initAction(anAction)
            updateIconFromStub(stub, anAction)
            return anAction
        }


        private fun updateIconFromStub(stub: ActionStubBase, anAction: AnAction) {
            val iconPath = stub.iconPath
            //TODO: Load Icon
            /*if (iconPath != null) {
                anAction.templatePresentation
                    .setIcon(
                        IconLoader
                    )
            }*/
        }


        private fun updateIcon(anAction: AnAction, iconPath: String) {

        }


        private fun convertGroupStub(
            stub: ActionGroupStub,
            actionManager: ActionManager
        ): ActionGroup? {
            val plugin: PluginDescriptor = stub.plugin
            val group =
                instantiate(stub.actionClass, plugin, ActionGroup::class.java) ?: return null
            stub.initGroup(group, actionManager)
            updateIconFromStub(stub, group)
            return group
        }

        private fun <T> instantiate(
            stubClassName: String,
            pluginDescriptor: PluginDescriptor,
            expectedClass: Class<T>
        ): T? {
            val obj: Any = try {
                ApplicationManager.getApplication()
                    .instantiateClass(stubClassName, pluginDescriptor)
            } catch (e: ProcessCanceledException) {
                throw e
            } catch (e: Throwable) {
                LOG.error(e)
                return null
            }
            if (expectedClass.isInstance(obj)) {
                return obj as T?
            }
            LOG.error(
                PluginException(
                    "class with name '" + stubClassName + "' must be an instance of '" + expectedClass.name + "'; " +
                            "got " + obj, pluginDescriptor.pluginId
                )
            )
            return null
        }

    }

}