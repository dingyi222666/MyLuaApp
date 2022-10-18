package com.dingyi.myluaapp.openapi.editor


/**
 * Provides services for getting the visible area of the editor and scrolling the editor.
 *
 * @see Editor.getScrollingModel
 */
interface ScrollingModel {

    fun scrollToCaret()
    fun scrollTo(pos: LogicalPosition)

}