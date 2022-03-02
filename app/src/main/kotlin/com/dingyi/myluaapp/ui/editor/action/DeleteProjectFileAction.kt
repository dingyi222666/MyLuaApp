package com.dingyi.myluaapp.ui.editor.action

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.dialog.builder.BottomDialogBuilder
import com.dingyi.myluaapp.common.dialog.layout.DefaultMessageLayout
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch
import java.io.File

class DeleteProjectFileAction:Action<(() -> Unit) -> Unit> {

    override fun callAction(argument: ActionArgument): (() -> Unit) -> Unit {
        var block: (() -> Unit)? = null
        val result = { target: () -> Unit ->
            block = target
        }

        val file = argument.getArgument<File>(0)

        val activity = argument.getArgument<AppCompatActivity>(1)

        val viewModel = argument.getArgument<MainViewModel>(2)
        activity?.let {

            BottomDialogBuilder.with(activity)
            .setDialogLayout(DefaultMessageLayout)
            .setTitle(R.string.editor_dialog_delete_title)
            .setMessage(R.string.editor_dialog_delete_message)
            .setPositiveButton(android.R.string.ok.getString()) { helper, _ ->
                helper.interceptClose(false)
                activity.lifecycleScope.launch {
                    viewModel?.deleteFile(
                            file ?: error("Want Delete Null")
                    )
                    block?.invoke()
                    helper.interceptClose(true)
                    helper.dismiss()
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
        }

        return result
    }



    override val name: String
        get() = "DeleteProjectFileAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action6"
}