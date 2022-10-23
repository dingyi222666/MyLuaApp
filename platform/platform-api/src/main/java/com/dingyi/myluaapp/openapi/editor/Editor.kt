package com.dingyi.myluaapp.openapi.editor

import android.view.View
import com.dingyi.myluaapp.openapi.project.Project
import com.intellij.openapi.util.UserDataHolder


/**
 * Represents an instance of a text editor.
 *
 * @see EditorFactory.createEditor
 * @see EditorFactory.createViewer
 */
interface Editor : UserDataHolder {
    companion object {
         val EMPTY_ARRAY = arrayOfNulls<Editor>(0)
    }

    /**
     * Returns the document edited or viewed in the editor.
     *
     * @return the document instance.
     */
    fun getDocument(): Document

    /**
     * Returns the component for the entire editor including the scrollbars, error stripe, gutter
     * and other decorations. The component can be used, for example, for converting logical to
     * screen coordinates.
     *
     * @return the component instance.
     */

    fun getView(): View


    /**
     * Returns the caret model for the document, which can be used to add and remove carets to the editor, as well as to query and update
     * carets' and corresponding selections' positions.
     *
     * @return the caret model instance.
     */

    fun getCaretModel(): CaretModel


    /**
     * Checks if this editor instance has been disposed.
     *
     * @return `true` if the editor has been disposed, `false` otherwise.
     */
    fun isDisposed(): Boolean

    /**
     * Returns the project to which the editor is related.
     *
     * @return the project instance, or `null` if the editor is not related to any project.
     */

    fun getProject(): Project


    /**
     * Returns the scrolling model for the document, which can be used to scroll the document
     * and retrieve information about the current position of the scrollbars.
     *
     * @return the scrolling model instance.
     */
    fun getScrollingModel(): ScrollingModel

    /**
     * Returns the selection model for the editor, which can be used to select ranges of text in
     * the document and retrieve information about the selection.
     *
     *
     * To query or change selections for specific carets, [CaretModel] interface should be used.
     *
     * @return the selection model instance.
     * @see .getCaretModel
     */

    fun getSelectionModel(): SelectionModel

}