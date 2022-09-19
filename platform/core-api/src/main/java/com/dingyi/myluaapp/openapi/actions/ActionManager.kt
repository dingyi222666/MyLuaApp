package com.dingyi.myluaapp.openapi.actions

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.openapi.application.ApplicationManager


/**
 * A manager for actions. Used to register and unregister actions, also
 * contains utility methods to easily fetch action by id and id by action.
 *
 * @see AnAction
 */
interface ActionManager {

    companion object {

        /**
         * Fetches the instance of ActionManager implementation.
         */
        fun getInstance(): ActionManager {
            return ApplicationManager.getIDEApplication().getService(getJavaClass())
        }
    }


    /**
     * Returns action associated with the specified actionId.
     *
     * @param actionId Id of the registered action
     * @return Action associated with the specified actionId, `null` if
     * there is no actions associated with the specified actionId
     * @throws IllegalArgumentException if `actionId` is `null`
     * @see com.intellij.openapi.actionSystem.IdeActions
     */
    fun getAction(actionId: String): AnAction

    /**
     * Returns actionId associated with the specified action.
     *
     * @return id associated with the specified action, `null` if action
     * is not registered
     * @throws IllegalArgumentException if `action` is `null`
     */

    fun getId(action: AnAction): String

    /**
     * Registers the specified action with the specified id. Note that the IDE's keymaps
     * processing deals only with registered actions.
     *
     * @param actionId Id to associate with the action
     * @param action   Action to register
     */
    fun registerAction(actionId: String, action: AnAction)

    /**
     * Registers the specified action with the specified id.
     *
     * @param actionId Id to associate with the action
     * @param action   Action to register
     * @param pluginId Identifier of the plugin owning the action. Used to show the actions in the
     * correct place under the "Plugins" node in the "Keymap" settings pane and similar dialogs.
     */
    fun registerAction(
        actionId: String,
        action: AnAction,
        pluginId: Any
    )

    /**
     * Unregisters the action with the specified actionId. **If you're going to register another action with the same ID, use [.replaceAction]
     * instead**, otherwise references in action groups may not be replaced.
     *
     * @param actionId Id of the action to be unregistered
     */
    fun unregisterAction(actionId: String)

    /**
     * Replaces an existing action with ID `actionId` by `newAction`. Using this method for changing behavior of a platform action
     * is not recommended, extract an extension point in the action implementation instead.
     */
    fun replaceAction(actionId: String, newAction: AnAction)


    /**
     * Returns the list of all registered action IDs with the specified prefix.
     */

    fun getActionIdList(idPrefix: String): List<String>

    /**
     * Checks if the specified action ID represents an action group and not an individual action.
     * Calling this method does not cause instantiation of a specific action class corresponding
     * to the action ID.
     *
     * @param actionId the ID to check.
     * @return `true` if the ID represents an action group, `false` otherwise.
     */
    fun isGroup(actionId: String): Boolean


    fun getActionOrStub(id: String): AnAction

}