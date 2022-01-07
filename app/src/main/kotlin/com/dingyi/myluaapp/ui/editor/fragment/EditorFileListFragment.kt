package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.dialog.builder.BottomDialogBuilder
import com.dingyi.myluaapp.common.dialog.layout.DefaultClickListLayout
import com.dingyi.myluaapp.common.dialog.layout.DefaultInputLayout
import com.dingyi.myluaapp.common.dialog.layout.DefaultMessageLayout
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.FragmentEditorFileListBinding

import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch

class EditorFileListFragment : BaseFragment<FragmentEditorFileListBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorFileListBinding {
        return FragmentEditorFileListBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(bundle: Bundle): EditorFileListFragment {
            return EditorFileListFragment().apply {
                arguments = bundle
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        viewBinding.list.apply {
            bindTitleView(viewBinding.title)
            projectPath = viewModel.controller.projectPath
            onEnterFile {
                viewModel.controller.postNowOpenedDir(it)
            }
            onClickFile {
                viewModel.controller.openFile(it)
                viewModel.refreshOpenedFile()
            }
            onEnterDir {
                this@EditorFileListFragment.loadFileList(it)
            }
            onLongClick { itemView, path ->
                showLongClickMenu(itemView, path)
            }
        }


        viewBinding.more.setOnClickListener { view ->
            R.menu.editor_file_list_toolbar.showPopMenu(
                view
            ) { menu ->
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.editor_file_list_toolbar_action_new_file -> {
                            showChooseFileTemplateDialog()
                            true
                        }
                        R.id.editor_file_list_toolbar_action_new_directory -> {
                            showCreateDirectoryDialog()
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        viewBinding.refresh.apply {
            setOnRefreshListener {
                refreshFileList()
            }
            setColorSchemeColors(requireContext().getAttributeColor(R.attr.colorPrimary))
        }
    }

    private fun showLongClickMenu(itemView: View, path: String) {
        R.menu.editor_file_list_long_click.showPopMenu(itemView) { menu ->
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editor_file_list_long_click_action_rename -> {
                        showRenameDialog(path)
                        true
                    }
                    R.id.editor_file_list_long_click_action_delete -> {
                        showDeleteDialog(path)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showDeleteDialog(path: String) {
        BottomDialogBuilder.with(requireActivity())
            .setDialogLayout(DefaultMessageLayout)
            .setTitle(R.string.editor_dialog_delete_title)
            .setMessage(R.string.editor_dialog_delete_message)
            .setPositiveButton(android.R.string.ok.getString()) { _, _ ->
                viewModel.deleteFile(path)
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()

    }

    private fun showRenameDialog(path: String) {
        BottomDialogBuilder.with(requireActivity())
            .setDialogLayout(DefaultInputLayout)
            .setTitle(R.string.editor_dialog_rename_title)
            .setDefaultText(path.toFile().name)
            .setPositiveButton(android.R.string.ok.getString()) { helper, inputText ->
                val (_, inputName) = inputText.checkNotNull()
                if (inputName.toString().isEmpty()) {
                    //拦截不关闭dialog
                    return@setPositiveButton helper.interceptClose(false)
                } else {
                    val parentPath = path.toFile().parentFile?.path ?: path
                    viewModel.rename(path,"$parentPath/$inputName")
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
    }

    private fun showCreateDirectoryDialog() {
        BottomDialogBuilder.with(requireActivity())
            .setDialogLayout(DefaultInputLayout)
            .setTitle(R.string.editor_dialog_create_directory_title)
            .setPositiveButton(android.R.string.ok.getString()) { helper, inputText ->
                val (_, inputName) = inputText.checkNotNull()
                if (inputName.toString().isEmpty()) {
                    //拦截不关闭dialog
                    return@setPositiveButton helper.interceptClose(false)
                } else {
                    val createDirectoryPath = "${viewBinding.list.nowOpenDir}/$inputName"
                    createDirectoryPath.toFile().mkdirs()

                    refreshFileList()
                    viewModel.controller.postNowOpenedDir(createDirectoryPath)
                    viewModel.refreshOpenedDir()
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
    }

    private fun showChooseFileTemplateDialog() {
        BottomDialogBuilder.with(requireActivity())
            .setTitle(R.string.editor_dialog_choose_file_template_title)
            .setPositiveButton(android.R.string.ok.getString()) { _, item ->
                item?.apply {
                    showInputPathDialog(item)
                }
            }
            .setDialogLayout(DefaultClickListLayout)
            .setSingleChoiceItems(
                viewModel.controller.getFileTemplates(
                    Paths.projectFileTemplateDir
                ), 0
            )
            .setNegativeButton(android.R.string.cancel.getString())
            .show()

    }

    private fun showInputPathDialog(item: Pair<String, Any>) {

        BottomDialogBuilder.with(requireActivity())
            .setTitle("${R.string.editor_dialog_create_file_title.getString()} ${item.first}")
            .setDialogLayout(DefaultInputLayout)
            .setPositiveButton(android.R.string.ok.getString()) { helper, inputText ->
                val (_, templateName) = item.checkNotNull()
                val (_, inputName) = inputText.checkNotNull()
                if (inputName.toString().isEmpty()) {
                    //拦截不关闭dialog
                    return@setPositiveButton helper.interceptClose(false)
                } else {
                    val createFilePath = viewModel.controller.createTemplateFile(
                        inputName.toString(),
                        viewBinding.list.nowOpenDir,
                        Paths.projectFileTemplateDir,
                        templateName.toString()
                    )
                    refreshFileList()
                    viewModel.controller.openFile(createFilePath)
                    viewModel.refreshOpenedFile()
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
    }


    private fun loadFileList(path: String) {

        lifecycleScope.launch {
            viewBinding.refresh.isRefreshing = true
            viewBinding.list.loadFileList(path)
            viewBinding.refresh.isRefreshing = false
        }
    }

    private fun refreshFileList() {
        lifecycleScope.launch {
            viewBinding.refresh.isRefreshing = true
            viewBinding.list.refreshFileList()
            viewBinding.refresh.isRefreshing = false
        }
    }

    private fun initViewModel() {

        viewModel.openedDir.observe(viewLifecycleOwner) {
            loadFileList(it)
        }

        viewModel.refreshOpenedDir()

    }


    override fun onDestroy() {
        super.onDestroy()

    }

}