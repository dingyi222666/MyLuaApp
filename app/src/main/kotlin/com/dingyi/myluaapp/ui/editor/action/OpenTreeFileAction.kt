package com.dingyi.myluaapp.ui.editor.action

import com.dingyi.myluaapp.common.kts.endsWith
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.MainViewModel
import java.io.File

class OpenTreeFileAction : Action<Unit> {


    override val name: String
        get() = "OpenTreeFile"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action1"

    override fun callAction(argument: ActionArgument): Unit? {
        //get file
        val file = argument.getArgument<File>(0)
        //get view model
        val viewModel = argument.getArgument<MainViewModel>(1)

        //if match file type
        if (file?.name?.endsWith(
                *argument.getPluginContext()
                    .getEditorService()
                    .getSupportLanguages().toTypedArray()
            ) == true
        ) {
            //open file
            viewModel?.openFile(file.path)
        }

        return null
    }


}