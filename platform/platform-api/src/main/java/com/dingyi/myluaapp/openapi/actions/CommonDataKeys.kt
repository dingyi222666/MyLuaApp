package com.dingyi.myluaapp.openapi.actions

import com.dingyi.myluaapp.ide.navigatable.Navigatable
import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.language.Language
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile


/**
 * Common data keys used as parameter for [AnActionEvent.getData] in [AnAction.update]/[AnAction.actionPerformed] implementations.
 *
 * @see com.intellij.openapi.actionSystem.PlatformDataKeys
 *
 * @see com.intellij.openapi.actionSystem.LangDataKeys
 */
object CommonDataKeys {
    val PROJECT: DataKey<Project> = DataKey.create("project")

    /**
     * Returns currently focused editor instance.
     *
     * @see .HOST_EDITOR
     *
     * @see .EDITOR_EVEN_IF_INACTIVE
     */
    val EDITOR: DataKey<Editor> = DataKey.create("editor")



    /**
     * Returns caret instance (in host or injected editor, depending on context).
     */
    val CARET: DataKey<Caret> = DataKey.create("caret")

    /**
     * Returns editor even if focus currently is in find bar.
     */
    val EDITOR_EVEN_IF_INACTIVE: DataKey<Editor> = DataKey.create("editor.even.if.inactive")

    /**
     * Returns [Navigatable] instance.
     *
     *
     * If [DataContext] has a value for [.PSI_ELEMENT] key which implements [Navigatable] it will automatically
     * return it for this key if explicit value isn't provided.
     */
    val NAVIGATABLE: DataKey<Navigatable> = DataKey.create("Navigatable")

    /**
     * Returns several navigatables, e.g. if several elements are selected in a component.
     *
     *
     * Note that if [DataContext] has a value for [.NAVIGATABLE] key it will automatically return single-element array for this
     * key if explicit value isn't provided so there is no need to perform such wrapping by hand.
     */
    val NAVIGATABLE_ARRAY: DataKey<Array<Navigatable>> = DataKey.create("NavigatableArray")

    /**
     * Returns [VirtualFile] instance.
     *
     *
     * Note that if [DataContext] has a value for [.PSI_FILE] key it will automatically return the corresponding [VirtualFile]
     * for this key if explicit value isn't provided. Also it'll return the containing file if a value for [.PSI_ELEMENT] key is provided.
     */
    val VIRTUAL_FILE: DataKey<VirtualFile> = DataKey.create("virtualFile")

    /**
     * Returns several [VirtualFile] instances, e.g. if several elements are selected in a component.
     *
     *
     * Note that if [DataContext] doesn't have an explicit value for this key it will automatically collect [VirtualFile] instances
     * corresponding to values provided for [.VIRTUAL_FILE], [.PSI_FILE], [.PSI_ELEMENT] and other keys and return the array
     * containing unique instances of the found files.
     */
    val VIRTUAL_FILE_ARRAY: DataKey<Array<VirtualFile>> = DataKey.create("virtualFileArray")


    val LANGUAGE: DataKey<Language> = DataKey.create("Language")


    /**
     * Returns whether the current location relates to a virtual space in an editor.
     *
     * @see com.intellij.openapi.editor.EditorSettings.setVirtualSpace
     */
    val EDITOR_VIRTUAL_SPACE: DataKey<Boolean> = DataKey.create("editor.virtual.space")
}
