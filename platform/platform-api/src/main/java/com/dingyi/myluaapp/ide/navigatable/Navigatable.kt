package com.dingyi.myluaapp.ide.navigatable


/**
 * Represents an instance which can be shown in the IDE (e.g. a file, a specific location inside a file, etc).
 *
 *
 * Many [com.intellij.psi.PsiElement]s implement this interface (see [com.intellij.psi.NavigatablePsiElement]). To create an
 * instance which opens a file in editor and put caret to a specific location use [com.intellij.openapi.fileEditor.OpenFileDescriptor].
 */
interface Navigatable {
    /**
     * Open editor and select/navigate to the object there if possible.
     * Just do nothing if navigation is not possible like in case of a package
     *
     * @param requestFocus `true` if focus requesting is necessary
     */
    fun navigate(requestFocus: Boolean)

    /**
     * Indicates whether this instance supports navigation of any kind.
     * Usually this method is called to ensure that navigation is possible.
     * Note that it is not called if navigation to source is supported,
     * i.e. [.canNavigateToSource] returns `true`.
     * We assume that this method should return `true` in such case,
     * so implement this method respectively.
     *
     * @return `false` if navigation is not possible for any reason.
     */
    fun canNavigate(): Boolean

    /**
     * Indicates whether this instance supports navigation to source (that means some kind of editor).
     * Note that navigation can be supported even if this method returns `false`.
     * In such cases it is not recommended to do batch navigation for all navigatables
     * available via [com.intellij.openapi.actionSystem.CommonDataKeys.NAVIGATABLE_ARRAY],
     * because it may lead to opening several modal dialogs.
     * Use [com.intellij.util.OpenSourceUtil.navigate] to process such arrays correctly.
     *
     * @return `false` if navigation to source is not possible for any reason.
     */
    fun canNavigateToSource(): Boolean

    companion object {
        val EMPTY_NAVIGATABLE_ARRAY = arrayOfNulls<Navigatable>(0)
    }
}
