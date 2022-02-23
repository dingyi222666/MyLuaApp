package com.dingyi.myluaapp.plugin.modules.default.action

import androidx.appcompat.widget.PopupMenu
import com.dingyi.myluaapp.common.kts.showToast
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.dsl.menu.click
import com.dingyi.myluaapp.plugin.dsl.menu.dsl
import com.dingyi.myluaapp.plugin.modules.default.editor.Editor
import com.google.android.material.tabs.TabLayout

class FileTagMenuAction : Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {

        val currentEditor = argument.getArgument<Editor>(1)

        val refreshFunction = argument.getArgument<()->Unit>(2)

        argument.getArgument<TabLayout.Tab>(0)?.let { tab ->

            val menu = PopupMenu(tab.view.context, tab.view)

            menu.menu.dsl {
                menu("保存").click {
                    currentEditor?.save()
                    "保存成功".showToast()
                    refreshFunction?.invoke()
                }
                menu("关闭当前").click {
                    currentEditor?.let { editor ->
                        argument
                            .getPluginContext()
                            .getEditorService()
                            .closeEditor(editor)
                    }
                    refreshFunction?.invoke()
                }
                menu("关闭其它").click {
                    currentEditor?.let { editor ->
                        argument
                            .getPluginContext()
                            .getEditorService()
                            .closeOtherEditor(editor)
                    }
                    refreshFunction?.invoke()
                }
                menu("关闭所有").click {
                    argument
                        .getPluginContext()
                        .getEditorService()
                        .closeAllEditor()
                    refreshFunction?.invoke()
                }
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