package com.dingyi.myluaapp.openapi.actions

import android.graphics.drawable.Drawable
import com.dingyi.myluaapp.openapi.project.Project
import java.util.function.Supplier


/**
 * Represents a menu that can be performed.
 *
 * For an action to do something, override the [AnAction.actionPerformed]
 * and optionally override [AnAction.update]. By implementing the
 * [AnAction.update] method, you can dynamically change the action's
 * presentation depending on the place. For more information on places see [ActionPlaces]
 *
 * <pre>
 * public class MyAction extends AnAction {
 * public MyAction() {
 * // ...
 * }
 *
 * public void update(AnActionEvent e) {
 * Presentation presentation = e.getPresentation();
 * presentation.setVisible(true);
 * presentation.setText(e.getPlace());
 * }
 *
 * public void actionPerformed(AnActionEvent e) {
 * // do something when this action is pressed
 * }
 * }
 * </pre>
 *
 * This implementation is partially adopted from IntelliJ's Actions API.
 *
 * @see AnActionEvent
 *
 * @see Presentation
 *
 * @see ActionPlaces
 */
abstract class AnAction() {

    private var myWorksInInjected: Boolean = true
    private lateinit var mTemplatePresentation: Presentation


    constructor(icon: Drawable?) : this(Presentation.NULL_STRING, Presentation.NULL_STRING, icon)
    constructor(dynamicText: Supplier<String?>) : this(
        dynamicText,
        Presentation.NULL_STRING,
        null
    )

    @JvmOverloads
    constructor(
        text: String?,
        description: String? = null,
        icon: Drawable? = null
    ) : this({ text }, { description }, icon)

    constructor(dynamicText: Supplier<String?>, icon: Drawable?) : this(
        dynamicText,
        Presentation.NULL_STRING,
        icon
    )

    constructor(
        dynamicText: Supplier<String?>,
        description: Supplier<String?>,
        icon: Drawable?
    ) : this() {
        templatePresentation.apply {
            setText(dynamicText)
            setDescription(description)
            setIcon(icon)
        }
    }


    /**
     * Returns a template presentation that will be used
     * as a template for created presentations.
     *
     * @return template presentation
     */

    val templatePresentation: Presentation
        get() {
            if (!this::mTemplatePresentation.isInitialized) {
                mTemplatePresentation = createTemplatePresentation()
            }
            return mTemplatePresentation
        }


    private fun createTemplatePresentation(): Presentation {
        return Presentation.newTemplatePresentation()
    }


    /**
     * Updates the state of this action. Default implementation does nothing.
     * Override this method to provide the ability to dynamically change action's
     * state and(or) presentation depending on the context. (For example
     * when your action state depends on the selection you can check for the
     * selection and change the state accordingly.)
     *
     * This method can be called frequently and on UI thread.
     * This means that this method is supposed to work really fast,
     * no work should be done at this phase. For example checking values such as editor
     * selection is fine but working with the file system such as reading/writing to a file is not.
     * If the action state cannot be determined, do the checks in
     * [actionPerformed] and inform the user if the action cannot
     * be performed by possibly showing a dialog.
     *
     * @param event Carries information on the invocation place and data available
     */
    fun update(event: AnActionEvent) {}

    /**
     * Implement this method to handle when this action has been clicked or pressed.
     *
     * @param e Carries information on the invocation place and data available.
     */
    abstract fun actionPerformed(e: AnActionEvent)


    /*    fun beforeActionPerformedUpdate(e: AnActionEvent) {
            update(e)
        }*/


    /**
     * Enables automatic detection of injected fragments in editor. Values in DataContext, passed to the action, like EDITOR, PSI_FILE
     * will refer to an injected fragment, if caret is currently positioned on it.
     */
    open fun setInjectedContext(worksInInjected: Boolean) {
        myWorksInInjected = worksInInjected
    }

    open fun isInInjectedContext(): Boolean {
        return myWorksInInjected
    }

    fun getTemplateText(): String? {
        return templatePresentation.getText()
    }


    /**
     * Same as [.update] but is calls immediately before actionPerformed() as final check guard.
     * Default implementation delegates to [.update].
     *
     * @param e Carries information on the invocation place and data available
     */
    open fun beforeActionPerformedUpdate(e: AnActionEvent) {
        val worksInInjected = isInInjectedContext()
        e.setInjectedContext(worksInInjected)
        update(e)
        if (!e.presentation.isEnabled() && worksInInjected) {
            e.setInjectedContext(false)
            update(e)
        }
    }


    open fun getEventProject(e: AnActionEvent): Project? {
        return e.getData(CommonDataKeys.PROJECT)
    }


    override fun toString(): String {
        return templatePresentation.toString()
    }

    companion object {
        val EMPTY_ARRAY: Array<AnAction> = emptyArray()
    }

}