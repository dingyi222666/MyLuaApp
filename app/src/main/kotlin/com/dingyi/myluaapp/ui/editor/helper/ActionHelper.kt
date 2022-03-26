package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
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
                    DefaultActionKey.CLICK_TREE_VIEW_FILE
                )
                registerAction(
                    getJavaClass<OpenLogFragmentAction>(),
                    activity.lifecycle,
                    DefaultActionKey.OPEN_LOG_FRAGMENT
                )
                registerAction(
                    getJavaClass<TreeListOnLongClickAction>(),
                    activity.lifecycle,
                    DefaultActionKey.TREE_LIST_ON_LONG_CLICK
                )
                registerAction(
                    getJavaClass<DeleteProjectFileAction>(),
                    activity.lifecycle,
                    DefaultActionKey.DELETE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectFileAction>(),
                    activity.lifecycle,
                    DefaultActionKey.CREATE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<RenameProjectFileAction>(),
                    activity.lifecycle,
                    DefaultActionKey.RENAME_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectDirectoryAction>(),
                    activity.lifecycle,
                    DefaultActionKey.CREATE_PROJECT_DIRECTORY
                )
                registerAction(
                    getJavaClass<BuildListenerAction>(),
                    activity.lifecycle,
                    DefaultActionKey.BUILD_STARTED_KEY
                )
                registerAction(
                    getJavaClass<FileTagMenuAction>(),
                    activity.lifecycle,
                    DefaultActionKey.SHOW_FILE_TAG_MENU
                )
                registerForwardArgument(
                    DefaultActionKey.DELETE_PROJECT_FILE,
                    DefaultActionKey.CREATE_PROJECT_FILE,
                    DefaultActionKey.RENAME_PROJECT_FILE,
                    DefaultActionKey.CREATE_PROJECT_DIRECTORY,
                    lifecycle = activity.lifecycle,
                ) {
                    it.addArgument(activity)
                        .addArgument(activity.viewModel)
                }
                registerForwardArgument(DefaultActionKey.BUILD_STARTED_KEY,activity.lifecycle) {
                    it.addArgument(activity.viewModel)
                }
                registerForwardArgument(DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST,activity.lifecycle) {
                    it.addArgument(activity.viewBinding.root)
                }
                registerForwardArgument(DefaultActionKey.OPEN_LOG_FRAGMENT,activity.lifecycle) {
                    it.addArgument(activity.viewBinding)
                }
            }
    }


}