package com.dingyi.myluaapp.ui.action

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.dialog.builder.BottomDialogBuilder
import com.dingyi.myluaapp.common.dialog.layout.DefaultInputLayout
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch
import java.io.File

class RenameProjectFileAction : Action<(() -> Unit) -> Unit> {

    override fun callAction(argument: ActionArgument): (() -> Unit) -> Unit {
        var block: (() -> Unit)? = null
        val result = { target: () -> Unit ->
            block = target
        }

        val file = argument.getArgument<File>(0).checkNotNull()

        val activity = argument.getArgument<AppCompatActivity>(1)

        val viewModel = argument.getArgument<MainViewModel>(2)
        activity?.let {
            BottomDialogBuilder.with(it)
                .setDialogLayout(DefaultInputLayout)
                .setTitle(R.string.editor_dialog_rename_title)
                .setDefaultText(file.name.toString())
                .setPositiveButton(android.R.string.ok.getString()) { helper, inputText ->
                    val (_, inputName) = inputText.checkNotNull()
                    if (inputName.toString().isEmpty()) {
                        //拦截不关闭dialog
                        return@setPositiveButton helper.interceptClose(false)
                    } else {
                        val parentPath = file.parentFile?.path ?: file
                        val targetPath = "$parentPath/$inputName".toFile()
                        helper.interceptClose(false)
                        activity.lifecycleScope.launch {
                            viewModel?.renameFile(file, targetPath)
                            block?.invoke()
                            helper.interceptClose(true)
                            helper.dismiss()
                        }

                    }
                }
                .setNegativeButton(android.R.string.cancel.getString())
                .show()
        }


        return result
    }


    override val name: String
        get() = "RenameProjectFileAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action7"
}