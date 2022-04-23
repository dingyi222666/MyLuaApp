package com.dingyi.myluaapp.openapi.action

import android.graphics.drawable.Drawable
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
</pre> *
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
    private var mTemplatePresentation: Presentation? = null


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

    @JvmOverloads
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

    val templatePresentation: Presentation
        get() {
            if (mTemplatePresentation == null) {
                mTemplatePresentation = createTemplatePresentation()
            }
            return checkNotNull(mTemplatePresentation)
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
     * [.actionPerformed] and inform the user if the action cannot
     * be performed by possibly showing a dialog.
     *
     * @param event Carries information on the invocation place and data available
     */
    fun update(event: AnActionEvent?) {}

    /**
     * Implement this method to handle when this action has been clicked or pressed.
     *
     * @param e Carries information on the invocation place and data available.
     */
    abstract fun actionPerformed(e: AnActionEvent)


    fun beforeActionPerformedUpdate(e: AnActionEvent) {
        update(e)
    }

    fun getTemplateText(): String? {
        return templatePresentation.getText()
    }


}