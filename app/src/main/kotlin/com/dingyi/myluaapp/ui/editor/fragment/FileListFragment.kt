package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.databinding.FragmentEditorFileListBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.adapter.FileListAdapter
import com.dingyi.myluaapp.ui.editor.presenter.FileListPresenter
import kotlinx.coroutines.launch
import livedatabus.LiveDataBus

/**
 * @author: dingyi
 * @date: 2021/8/11 4:18
 * @description:
 **/
class FileListFragment : BaseFragment<FragmentEditorFileListBinding, MainViewModel>() {

    private val presenter by lazy {
        FileListPresenter(this, viewModel)
    }

    private val fileListAdapter = FileListAdapter()

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewBinding.apply {
            list.apply {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                adapter = fileListAdapter
            }

            refresh.apply {
                setOnRefreshListener {
                    refreshFileList()
                }
                setColorSchemeColors(requireContext().getAttributeColor(R.attr.colorPrimary))
            }
        }

        fileListAdapter.setOnItemClickListener {
            if (!viewBinding.refresh.isRefreshing) {
                presenter.loadFileList(it) { path ->
                    val file = path.toFile()
                    when {
                        file.isDirectory -> refreshFileList(path)
                        file.isFile -> {
                            LiveDataBus.getDefault()
                                .with("openPath", String::class.java)
                                .value = path
                        }
                    }
                }
            }
        }

        observableViewModel()

        viewModel.fileListTitle.value = viewModel.projectConfig.value?.lastOpenDir

        refreshFileList()


    }

    private fun refreshFileList(path: String? = viewModel.projectConfig.value?.lastOpenDir) {
        lifecycleScope.launch {
            viewBinding.refresh.isRefreshing = true
            path?.run {
                presenter.refreshFileList(this)
            }
            viewBinding.refresh.isRefreshing = false
        }
    }

    private fun observableViewModel() {
        viewModel.apply {
            fileListTitle.observe(viewLifecycleOwner) {
                viewBinding.title.text = it
            }
            fileList.observe(viewLifecycleOwner) {
                presenter.mapToPairList(it).apply {
                    fileListAdapter.submitList(this)
                }
            }
        }

    }


    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorFileListBinding {
        return FragmentEditorFileListBinding.inflate(inflater, container, false)
    }
}

