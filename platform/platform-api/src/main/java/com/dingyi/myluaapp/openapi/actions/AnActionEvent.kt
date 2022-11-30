package com.dingyi.myluaapp.openapi.actions

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.project.Project
import org.jetbrains.annotations.NonNls


/**
 * Container for the information necessary to execute or update an [AnAction].
 *
 * @see AnAction.actionPerformed
 * @see AnAction.update
 */
class AnActionEvent @JvmOverloads constructor(
    dataContext: DataContext,
    @NonNls place: String,
    presentation: Presentation,
    actionManager: ActionManager,
    isContextMenuAction: Boolean = false,
    isActionToolbar: Boolean = false
) : PlaceProvider {
    val actionManager: ActionManager
    private val myDataContext: DataContext
    private val myPlace: String

    /**
     * Returns the presentation which represents the action in the place from where it is invoked
     * or updated.
     *
     * @return the presentation instance.
     */
    val presentation: Presentation


    val isFromContextMenu: Boolean
    val isFromActionToolbar: Boolean
    var isInInjectedContext = false
        private set

    /**
     * @throws IllegalArgumentException if `dataContext` is `null` or
     * `place` is `null` or `presentation` is `null`
     *
     * @see ActionManager.getInstance
     */
    /**
     * @throws IllegalArgumentException if `dataContext` is `null` or
     * `place` is `null` or `presentation` is `null`
     *
     * @see ActionManager.getInstance
     */
    init {
        this.actionManager = actionManager
        myDataContext = dataContext
        myPlace = place
        this.presentation = presentation
        isFromContextMenu = isContextMenuAction
        isFromActionToolbar = isActionToolbar
    }

    fun withDataContext(dataContext: DataContext): AnActionEvent {
        if (myDataContext === dataContext) return this
        val event = AnActionEvent(
            dataContext, myPlace,
            presentation,
            actionManager,
            isFromContextMenu,
            isFromActionToolbar
        )
        event.setInjectedContext(isInInjectedContext)
        return event
    }


    /**
     * @return Project from the context of this event.
     */
    val project: Project?
        get() = getData(CommonDataKeys.PROJECT)

    /**
     * Returns the context which allows to retrieve information about the state of IDE related to
     * the action invocation (active editor, selection and so on).
     *
     * @return the data context instance.
     */
    val dataContext: DataContext
        get() = if (isInInjectedContext) getInjectedDataContext(myDataContext) else myDataContext

    fun <T> getData(key: DataKey<T>): T? {
        return dataContext.getData(key)
    }

    /**
     * Returns not null data by a data key. This method assumes that data has been checked for `null` in `AnAction#update` method.
     * <br></br><br></br>
     * Example of proper usage:
     *
     * <pre>
     *
     * public class MyAction extends AnAction {
     * public void update(AnActionEvent e) {
     * // perform action if and only if EDITOR != null
     * boolean enabled = e.getData(CommonDataKeys.EDITOR) != null;
     * e.getPresentation().setEnabled(enabled);
     * }
     *
     * public void actionPerformed(AnActionEvent e) {
     * // if we're here then EDITOR != null
     * Document doc = e.getRequiredData(CommonDataKeys.EDITOR).getDocument();
     * doSomething(doc);
     * }
     * }
     *
    </pre> *
     */
    fun <T> getRequiredData(key: DataKey<T>): T {
        return checkNotNull(getData(key))
    }

    /**
     * Returns the identifier of the place in the IDE user interface from where the action is invoked
     * or updated.
     *
     * @return the place identifier
     * @see com.intellij.openapi.actionSystem.ActionPlaces
     */
    override fun getPlace(): String {
        return myPlace
    }

    fun setInjectedContext(worksInInjected: Boolean) {
        isInInjectedContext = worksInInjected
    }


    interface InjectedDataContextSupplier {
        val injectedDataContext: DataContext
    }

    private class InjectedDataContext internal constructor(context: DataContext) :
        DataContextWrapper(context) {
        override fun getData(@NonNls dataId: String): Any? {
            val injectedId = InjectedDataKeys.injectedId(dataId)
            val injected = if (injectedId != null) super.getData(injectedId) else null
            return injected ?: super.getData(dataId)
        }
    }

    companion object {


        fun createFromAnAction(
            action: AnAction,
            place: String,
            dataContext: DataContext
        ): AnActionEvent {
            val presentation = action.templatePresentation.clone()
            val anActionEvent = AnActionEvent(
                dataContext,
                place,
                presentation,
                ActionManager.getInstance(),
            )
            anActionEvent.setInjectedContext(action.isInInjectedContext())
            return anActionEvent
        }

        fun createFromDataContext(
            place: String,
            presentation: Presentation?,
            dataContext: DataContext
        ): AnActionEvent {
            return AnActionEvent(
                dataContext, place,
                presentation ?: Presentation(), ActionManager.getInstance()
            )
        }

        @JvmOverloads

        fun getInjectedDataContext(dataContext: DataContext): DataContext {
            if (dataContext is InjectedDataContextSupplier) {
                return (dataContext as InjectedDataContextSupplier).injectedDataContext
            }
            return if (dataContext is InjectedDataContext) dataContext else InjectedDataContext(
                dataContext
            )
        }
    }
}
