package com.dingyi.myluaapp.plugin.modules.default.action

import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument

class SymbolClickAction:Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {
        val text = argument.getArgument<String>(0)

        val currentEditor = argument.getPluginContext().getEditorService().getCurrentEditor()

        currentEditor?.appendText(text.toString())

        return null
    }

    override val name: String
        get() ="symbol_click"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.default.action2"
}