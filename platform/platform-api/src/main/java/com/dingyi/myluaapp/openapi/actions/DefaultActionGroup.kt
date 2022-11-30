package com.dingyi.myluaapp.openapi.actions


import com.dingyi.myluaapp.diagnostic.Logger
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.util.FunctionUtil
import com.intellij.util.containers.ContainerUtil
import java.util.Arrays
import java.util.function.Supplier


/**
 * A default implementation of [ActionGroup]. Provides the ability
 * to add children actions and separators between them. In most of the
 * cases you will be using this implementation but note that there are
 * cases (for example "Recent files" dialog) where children are determined
 * on rules different than just positional constraints, that's when you need
 * to implement your own `ActionGroup`.
 *
 * @see Constraints
 *
 *
 * @see com.intellij.openapi.actionSystem.ComputableActionGroup
 *
 *
 * @see com.intellij.ide.actions.NonEmptyActionGroup
 *
 * @see com.intellij.ide.actions.NonTrivialActionGroup
 *
 * @see com.intellij.ide.actions.SmartPopupActionGroup
 */
open class DefaultActionGroup protected constructor(
    shortName: Supplier<String?>?,
    popup: Boolean
) : ActionGroup(shortName, popup) {
    /**
     * Contains instances of AnAction
     */
    private val mySortedChildren: MutableList<AnAction> =
        ContainerUtil.createLockFreeCopyOnWriteList()


    var modificationStamp = 0
        private set

    constructor() : this(Presentation.NULL_STRING, false) {}


    /**
     * Creates an action group containing the specified actions.
     *
     * @param actions the actions to add to the group
     */
    constructor(actions: List<AnAction>) : this(Presentation.NULL_STRING, actions) {}
    constructor(name: Supplier<String?>, actions: List<AnAction>) : this(
        name,
        false
    ) {
        addActions(actions)
    }

    constructor(
        name: String?,
        actions: List<AnAction>
    ) : this({ name }, actions) {
    }

    constructor(shortName: String?, popup: Boolean) : this(
        { shortName },
        popup
    ) {
    }

    private fun incrementModificationStamp() {
        modificationStamp++
    }

    private fun addActions(actions: List<AnAction>) {
        val actionSet: MutableSet<Any> = HashSet()
        val uniqueActions: MutableList<AnAction> = ArrayList(actions.size)
        for (action in actions) {
            require(!(action === this)) { CANT_ADD_ITSELF + action }
            if (/*action !is Separator &&*/ !actionSet.add(action)) {
                LOG.error(CANT_ADD_ACTION_TWICE + action)
                continue
            }
            uniqueActions.add(action)
        }
        mySortedChildren.addAll(uniqueActions)
        incrementModificationStamp()
    }

    /**
     * Adds the specified action to the tail.
     *
     * @param action        Action to be added
     * @param actionManager ActionManager instance
     */
    fun add(action: AnAction, actionManager: ActionManager) {
        addAction(action, actionManager)
    }

    fun add(action: AnAction) {
        addAction(action)
    }

    fun addAction(action: AnAction): ActionInGroup {
        return addAction(action, ActionManager.getInstance())
    }


    fun addAction(
        action: AnAction,
        actionManager: ActionManager
    ): ActionInGroup {

        require(!(action === this)) { CANT_ADD_ITSELF + action }

        // check that action isn't already registered
        if (/*action !is Separator &&*/ containsAction(action)) {
            LOG.error(CANT_ADD_ACTION_TWICE + action)
            remove(action, actionManager.getId(action))
        }
        /* constraint = constraint.clone() as Constraints
         if (constraint.myAnchor === Anchor.FIRST) {
             mySortedChildren.add(0, action)
         } else if (constraint.myAnchor === Anchor.LAST) {*/
        mySortedChildren.add(action)
        /* } else {
             myPairs.add(Pair.create(action, constraint))
         }*/
        incrementModificationStamp()
        return ActionInGroup(this, action)
    }

    private fun containsAction(action: AnAction): Boolean {
        return mySortedChildren.contains(action)
    }

    /**
     * Removes specified action from group.
     *
     * @param action Action to be removed
     */
    fun remove(action: AnAction) {
        remove(action, ActionManager.getInstance())
    }

    fun remove(action: AnAction, actionManager: ActionManager) {
        remove(action, actionManager.getId(action))
    }

    fun remove(action: AnAction, id: String?) {
        mySortedChildren.remove(action)

        mySortedChildren.removeIf { oldAction: AnAction? -> oldAction is ActionStubBase && (oldAction as ActionStubBase).id == id }
        /* ) {
             for (i in myPairs.indices) {
                 val (first) = myPairs[i]
                 if (first.equals(action) || first is ActionStubBase && (first as ActionStubBase).id == id) {
                     myPairs.removeAt(i)*/
        incrementModificationStamp()
        /*break
    }
}
}*/
    }

    /**
     * Removes all children actions (separators as well) from the group.
     */
    fun removeAll() {
        mySortedChildren.clear()
        /*myPairs.clear()*/
        incrementModificationStamp()
    }

    /**
     * Replaces specified action with the a one.
     */
    fun replaceAction(oldAction: AnAction,/* @NotNull*/ newAction: AnAction): Boolean {
        val index = mySortedChildren.indexOf(oldAction)
        /*  if (index >= 0) {*/
        mySortedChildren[index] = newAction
        incrementModificationStamp()
        return true
        /*  } else {
              for (i in myPairs.indices) {
                  val (first, second) = myPairs[i]
                  if (first.equals(newAction)) {
                      myPairs[i] = Pair.create(newAction, second)
                      incrementModificationStamp()
                      return true
                  }
              }
          }*/

    }

    /**
     * Copies content from `group`.
     * @param other group to copy from
     */
    fun copyFromGroup(other: DefaultActionGroup) {
        /*  copyFrom(other)*/
        setPopup(other.isPopup())
        mySortedChildren.clear()
        mySortedChildren.addAll(other.mySortedChildren)
        /*  myPairs.clear()
          myPairs.addAll(other.myPairs)*/
        incrementModificationStamp()
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return getChildren(e, if (e != null) e.actionManager else ActionManager.getInstance())
    }

    /**
     * Returns group's children in the order determined by constraints.
     *
     * @param e not used
     * @return An array of children actions
     */
    override fun getChildren(
        e: AnActionEvent?,
        actionManager: ActionManager
    ): Array<AnAction> {
        var hasNulls = false
        // Mix sorted actions and pairs
        val sortedSize = mySortedChildren.size
        val children = arrayOfNulls<AnAction>(sortedSize)
        for (i in 0 until sortedSize) {
            var action: AnAction? = mySortedChildren[i]
            if (action is ActionStubBase) {
                action = unStub(actionManager, action as ActionStubBase?)
                if (action == null) {
                    LOG.error("Can't unstub " + mySortedChildren[i])
                } else {
                    mySortedChildren[i] = action
                }
            }
            hasNulls = hasNulls or (action == null)
            children[i] = action
        }

        return if (hasNulls) {
            ContainerUtil.mapNotNull(children, FunctionUtil.id(), EMPTY_ARRAY)
        } else children.requireNoNulls()
    }


    private fun unStub(
        actionManager: ActionManager,
        stub: ActionStubBase?
    ): AnAction? {
        return try {
            val action = actionManager.getAction(stub?.id ?: "")
            if (action == null) {
                LOG.error("Null child action in group " + this + " of class " + javaClass + ", id=" + stub?.id)
                return null
            }
            replace(stub as AnAction, action)
            action
        } catch (ex: ProcessCanceledException) {
            throw ex
        } catch (e1: Throwable) {
            LOG.error(e1)
            null
        }
    }

    /**
     * Returns the number of contained children (including separators).
     *
     * @return number of children in the group
     */
    val childrenCount: Int
        get() = mySortedChildren.size


    fun addAll(group: ActionGroup) {
        addAll(*group.getChildren(null))
    }

    fun addAll(actionList: Collection<AnAction>) {
        addAll(actionList, ActionManager.getInstance())
    }

    fun addAll(actionList: Collection<AnAction>, actionManager: ActionManager) {
        if (actionList.isEmpty()) {
            return
        }
        for (action in actionList) {
            addAction(action, actionManager)
        }
    }

    fun addAll(vararg actions: AnAction) {
        if (actions.isEmpty()) {
            return
        }
        val actionManager = ActionManager.getInstance()
        for (action in actions) {
            addAction(action, actionManager)
        }
    }

    companion object {
        private val LOG: Logger = Logger.getInstance(DefaultActionGroup::class.java)
        private const val CANT_ADD_ITSELF = "Cannot add a group to itself: "
        private const val CANT_ADD_ACTION_TWICE = "Cannot add an action twice: "
        fun createPopupGroup(shortName: Supplier<String?>?): DefaultActionGroup {
            return DefaultActionGroup(shortName, true)
        }

        fun createFlatGroup(shortName: Supplier<String?>?): DefaultActionGroup {
            return DefaultActionGroup(shortName, false)
        }

        fun createPopupGroupWithEmptyText(): DefaultActionGroup {
            return createPopupGroup { "" }
        }

        private fun findIndex(
            actionId: String,
            actions: List<AnAction>,
            actionManager: ActionManager
        ): Int {
            for (i in actions.indices) {
                val action = actions[i]
                if (action is ActionStubBase) {
                    if ((action as ActionStubBase).id == actionId) {
                        return i
                    }
                } else {
                    val id = actionManager.getId(action)
                    if (id != null && id == actionId) {
                        return i
                    }
                }
            }
            return -1
        }


    }
}
