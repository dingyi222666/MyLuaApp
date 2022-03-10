package com.dingyi.myluaapp.ui.action

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.dialog.builder.BottomDialogBuilder
import com.dingyi.myluaapp.common.dialog.layout.DefaultClickListLayout
import com.dingyi.myluaapp.common.dialog.layout.DefaultInputLayout
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.project.FileTemplate
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch
import java.io.File

class CreateProjectFileAction : Action<(() -> Unit) -> Unit> {

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
                .setTitle(R.string.editor_dialog_choose_file_template_title)
                .setPositiveButton(android.R.string.ok.getString()) { _, item ->
                    item?.apply {
                        showInputPathDialog(
                            item,
                            file.checkNotNull(),
                            activity,
                            viewModel.checkNotNull(),
                            block.checkNotNull()
                        )
                    }
                }
                .setDialogLayout(DefaultClickListLayout)
                .setSingleChoiceItems(
                    viewModel?.project?.value?.getFileTemplates()?.map { it.name to it }
                        ?: listOf(), 0
                )
                .setNegativeButton(android.R.string.cancel.getString())
                .show()

        }

        return result
    }

    private fun showInputPathDialog(
        item: Pair<String, Any>,
        file: File,
        activity: AppCompatActivity,
        viewModel: MainViewModel,
        block: (() -> Unit)
    ) {

        BottomDialogBuilder.with(activity)
            .setTitle("${R.string.editor_dialog_create_file_title.getString()} ${item.first}")
            .setDialogLayout(DefaultInputLayout)
            .setPositiveButton(android.R.string.ok.getString()) { helper, inputText ->
                val (_, template) = item.checkNotNull()
                val (_, inputName) = inputText.checkNotNull()
                if (inputName.toString().isEmpty()) {
                    //拦截不关闭dialog
                    return@setPositiveButton helper.interceptClose(false)
                } else {
                    helper.interceptClose(false)
                    activity.lifecycleScope.launch {
                        viewModel.createFile(template as FileTemplate, file, inputName.toString())
                        block.invoke()
                        helper.interceptClose(true)
                        helper.dismiss()
                    }
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
    }


    override val name: String
        get() = "DeleteProjectFileAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action6"
}