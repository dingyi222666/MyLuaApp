package com.dingyi.myluaapp.openapi.action

import androidx.annotation.RestrictTo
import org.jetbrains.kotlin.com.intellij.openapi.util.Key

/**
 * Container for information necessary to execute or update an {@link AnAction}
 *
 * @see AnAction.update(AnActionEvent)
 * @see AnAction.actionPerformed(AnActionEvent)
 */

class AnActionEvent(
     private val mDataContext: DataContext,
     private val mPlace: String,
     private var mPresentation: Presentation,
) : PlaceProvider {

    fun setPresentation(presentation: Presentation) {
        mPresentation = presentation
    }

    override fun getPlace(): String {
        return mPlace
    }

    fun getPresentation(): Presentation {
        return mPresentation
    }


    /**
     * Allows an action to retrieve information about the context in which it was invoked.
     * @see DataContext#getData(Key)
     */
    fun <T> getData(key: DataKey<T>): T? {
        return mDataContext.getData(key)
    }

    /**
     * Returns a non null data by a data key. This method assumes that data has been checked
     * for `null` in `AnAction#update` method.
     *
     * <br></br><br></br>
     * Example of proper usage:
     *
     * <pre>
     * public class MyAction extends AnAction {
     * public void update(AnActionEvent e) {
     * // perform action if and only if EDITOR != null
     * boolean visible = e.getData(CommonDataKeys.EDITOR) != null;
     * e.getPresentation().setVisible(visible);
     * }
     *
     * public void actionPerformed(AnActionEvent e) {
     * // if we're here then EDITOR != null
     * Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
     * }
     * }</pre> *
     */
    fun <T> getRequiredData(key: DataKey<T>): T? {
        return getData(key)
    }

    /**
     * Returns the data context which allows to retrieve information about the state of the IDE
     * related to the action invocation. (Active editor, fragment and so on)
     *
     * @return the data context instance
     */
    fun getDataContext(): DataContext {
        return mDataContext
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun <T> injectData(key: DataKey<T>, value: T) {
        mDataContext.putData(key, value)
    }


}