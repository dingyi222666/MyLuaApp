package com.dingyi.myluaapp.ui.editor.action

import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.action.ActionKey
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.view.treeview.TreeNode

class TreeListOnLongClickAction : Action<(() -> Unit) -> Unit> {


    override fun callAction(argument: ActionArgument): (() -> Unit) -> Unit {
        var block: (() -> Unit)? = null
        val result = { target: () -> Unit ->
            block = target
        }

        val treeNode = argument.getArgument<TreeNode>(0)

        argument.getArgument<View>(1)?.let { itemView ->
            val menu = PopupMenu(itemView.context, itemView)
            menu.inflate(R.menu.editor_file_list_long_click)

            if (treeNode?.isLeaf == false) {
                menu.menu
                    .findItem(R.id.editor_file_list_toolbar_action_new_directory)
                    .isVisible = false

                menu.menu
                    .findItem(R.id.editor_file_list_toolbar_action_new_file)
                    .isVisible = false
            }

            menu.show()
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editor_file_list_toolbar_action_new_file ->
                        callAction(argument, DefaultActionKey.CREATE_PROJECT_FILE, treeNode, block)
                    R.id.editor_file_list_long_click_action_rename ->
                        callAction(argument, DefaultActionKey.RENAME_PROJECT_FILE, treeNode, block)
                    R.id.editor_file_list_long_click_action_delete ->
                        callAction(argument, DefaultActionKey.DELETE_PROJECT_FILE, treeNode, block)
                    R.id.editor_file_list_toolbar_action_new_directory ->
                        callAction(argument,DefaultActionKey.CREATE_PROJECT_DIRECTORY,treeNode,block)
                    else -> false
                }
                true
            }
        }

        return result
    }


    private fun callAction(
        argument: ActionArgument,
        actionKey: ActionKey,
        treeNode: TreeNode?,
        block: (() -> Unit)?
    ) {
        argument
            .getPluginContext()
            .getActionService()
            .callAction<((() -> Unit) -> Unit)>(
                argument.getPluginContext().getActionService().createActionArgument()
                    .addArgument(treeNode?.value),
                actionKey
            )?.invoke {
                block?.invoke()
            }
    }

    override val name: String
        get() = "TreeListOnLongClickAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action5"
}