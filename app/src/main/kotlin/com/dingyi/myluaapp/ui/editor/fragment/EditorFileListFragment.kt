package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.showPopMenu
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
        }


        viewBinding.more.setOnClickListener { view ->
            R.menu.editor_file_list_toolbar.showPopMenu(
                view
            ) { menu ->
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.editor_file_list_toolbar_action_new_file -> {
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