package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.modules.default.action.CommonActionKey
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.action.*
import com.dingyi.myluaapp.ui.editor.activity.EditorActivity

object ActionHelper {
    fun initEditorActivity(activity: EditorActivity) {
        PluginModule
            .getActionService().apply {
                registerAction(
                    getJavaClass<OpenTreeFileAction>(),
                    activity.lifecycle,
                    CommonActionKey.CLICK_TREE_VIEW_FILE
                )
                registerAction(
                    getJavaClass<OpenLogFragmentAction>(),
                    activity.lifecycle,
                    CommonActionKey.OPEN_LOG_FRAGMENT
                )
                registerAction(
                    getJavaClass<TreeListOnLongClickAction>(),
                    activity.lifecycle,
                    CommonActionKey.TREE_LIST_ON_LONG_CLICK
                )
                registerAction(
                    getJavaClass<DeleteProjectFileAction>(),
                    activity.lifecycle,
                    CommonActionKey.DELETE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectFileAction>(),
                    activity.lifecycle,
                    CommonActionKey.CREATE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<RenameProjectFileAction>(),
                    activity.lifecycle,
                    CommonActionKey.RENAME_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectDirectoryAction>(),
                    activity.lifecycle,
                    CommonActionKey.CREATE_PROJECT_DIRECTORY
                )
                registerAction(
                    getJavaClass<BuildListenerAction>(),
                    activity.lifecycle,
                    CommonActionKey.BUILD_STARTED_KEY
                )
                registerAction(
                    getJavaClass<FileTagMenuAction>(),
                    activity.lifecycle,
                    CommonActionKey.SHOW_FILE_TAG_MENU
                )
                registerForwardArgument(
                    CommonActionKey.DELETE_PROJECT_FILE,
                    CommonActionKey.CREATE_PROJECT_FILE,
                    CommonActionKey.RENAME_PROJECT_FILE,
                    CommonActionKey.CREATE_PROJECT_DIRECTORY,
                    lifecycle = activity.lifecycle,
                ) {
                    it.addArgument(activity)
                        .addArgument(activity.viewModel)
                }
                registerForwardArgument(CommonActionKey.BUILD_STARTED_KEY,activity.lifecycle) {
                    it.addArgument(activity.viewModel)
                }
                registerForwardArgument(CommonActionKey.OPEN_EDITOR_FILE_DELETE_TOAST,activity.lifecycle) {
                    it.addArgument(activity.viewBinding.root)
                }
                registerForwardArgument(CommonActionKey.OPEN_LOG_FRAGMENT,activity.lifecycle) {
                    it.addArgument(activity.viewBinding)
                }
            }
    }


}