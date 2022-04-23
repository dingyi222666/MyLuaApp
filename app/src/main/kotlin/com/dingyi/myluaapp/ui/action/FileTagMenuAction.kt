package com.dingyi.myluaapp.ui.action

import androidx.appcompat.widget.PopupMenu
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.showToast
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.modules.default.editor.Editor
import com.google.android.material.tabs.TabLayout

class FileTagMenuAction : Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {

        val currentEditor = argument.getArgument<Editor>(1)

        val refreshFunction = argument.getArgument<()->Unit>(2)

        argument.getArgument<TabLayout.Tab>(0)?.let { tab ->

            val menu = PopupMenu(tab.view.context, tab.view)

            menu.inflate(R.menu.editor_tab_menu)

            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editor_action_save -> {
                        currentEditor?.save()
                        R.string.editor_menu_save_success.getString().showToast()
                        refreshFunction?.invoke()
                    }
                    R.id.editor_action_close -> {
                        currentEditor?.let { editor ->
                            argument
                                .getPluginContext()
                                .getEditorService()
                                .closeEditor(editor)
                        }
                        refreshFunction?.invoke()
                    }
                    R.id.editor_action_close_other -> {
                        currentEditor?.let { editor ->
                            argument
                                .getPluginContext()
                                .getEditorService()
                                .closeOtherEditor(editor)
                        }
                        refreshFunction?.invoke()
                    }
                    R.id.editor_action_close_all -> {
                        argument
                            .getPluginContext()
                            .getEditorService()
                            .closeAllEditor()
                        refreshFunction?.invoke()
                    }
                    else -> return@setOnMenuItemClickListener false
                }

                true
            }


            menu.show()

        }

        return null

    }

    override val name: String
        get() = "file_tag_menu"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.default.action4"
}