package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.action.*
import com.dingyi.myluaapp.ui.editor.activity.EditorActivity

object ActionHelper {
    fun initEditorActivity(activity: EditorActivity) {
        PluginModule
            .getActionService().apply {
                registerAction(
                    getJavaClass<OpenTreeFileAction>(),
                    DefaultActionKey.CLICK_TREE_VIEW_FILE
                )
                registerAction(
                    getJavaClass<OpenLogFragmentAction>(),
                    DefaultActionKey.OPEN_LOG_FRAGMENT
                )
                registerAction(
                    getJavaClass<TreeListOnLongClickAction>(),
                    DefaultActionKey.TREE_LIST_ON_LONG_CLICK
                )
                registerAction(
                    getJavaClass<DeleteProjectFileAction>(),
                    DefaultActionKey.DELETE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectFileAction>(),
                    DefaultActionKey.CREATE_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<RenameProjectFileAction>(),
                    DefaultActionKey.RENAME_PROJECT_FILE
                )
                registerAction(
                    getJavaClass<CreateProjectDirectoryAction>(),
                    DefaultActionKey.CREATE_PROJECT_DIRECTORY
                )
                registerForwardArgument(
                    DefaultActionKey.DELETE_PROJECT_FILE,
                    DefaultActionKey.CREATE_PROJECT_FILE,
                    DefaultActionKey.RENAME_PROJECT_FILE,
                    DefaultActionKey.CREATE_PROJECT_DIRECTORY
                ) {
                    it.addArgument(activity)
                        .addArgument(activity.viewModel)
                }

                registerForwardArgument(DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST) {
                    it.addArgument(activity.viewBinding.root)
                }
                registerForwardArgument(DefaultActionKey.OPEN_LOG_FRAGMENT) {
                    it.addArgument(activity.viewBinding)
                }
            }
    }
}