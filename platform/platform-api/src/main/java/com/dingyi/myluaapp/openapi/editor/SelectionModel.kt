package com.dingyi.myluaapp.openapi.editor


/**
 * Provides services for selecting text in the IDE's text editor and retrieving information about the selection.
 * Most of the methods here exist for compatibility reasons, corresponding functionality is also provided by [CaretModel] now.
 *
 *
 *
 * @see Editor.getSelectionModel
 */
interface SelectionModel {
    /**
     * @return the editor this selection model belongs to
     */
    fun getEditor(): Editor

}