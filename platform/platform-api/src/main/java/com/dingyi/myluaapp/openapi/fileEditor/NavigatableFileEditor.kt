package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.ide.navigatable.Navigatable

/**
 * File editor which supports possibility to navigate to [Navigatable] element
 *
 * @author spleaner
 * @see FileEditorNavigatable
 */
interface NavigatableFileEditor : FileEditor {
    /**
     * Check whatever the editor can navigate to the given element
     *
     * @return true if editor can navigate, false otherwise
     */
    fun canNavigateTo(navigatable: Navigatable): Boolean

    /**
     * Navigate editor to the given navigatable if [.canNavigateTo] is true
     *
     * @param navigatable navigation target
     */
    fun navigateTo(navigatable: Navigatable)
}
