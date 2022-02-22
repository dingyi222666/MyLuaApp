package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.FragmentEditorFileListBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.adapter.EditorNodeBinder
import com.dingyi.myluaapp.ui.editor.helper.TreeHelper
import com.dingyi.view.treeview.TreeNode
import com.dingyi.view.treeview.TreeView
import com.dingyi.view.treeview.base.BaseNodeViewBinder
import com.dingyi.view.treeview.base.BaseNodeViewFactory


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class EditorFileListFragment : BaseFragment<FragmentEditorFileListBinding, MainViewModel>() {

    private lateinit var rootNode: TreeNode

    private lateinit var treeView: TreeView

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorFileListBinding {
        return FragmentEditorFileListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootNode = TreeNode.root()

        treeView = TreeView(rootNode, requireContext(), object : BaseNodeViewFactory() {
            override fun getNodeViewBinder(view: View, viewType: Int): BaseNodeViewBinder {
                return EditorNodeBinder(view,viewModel)
            }

            override fun getNodeLayoutId(level: Int): Int {
                return R.layout.layout_item_editor_file_list
            }
        })



        viewBinding.apply {
            root
                .addView(treeView.view, LinearLayoutCompat.LayoutParams(-1, -1))

            title
                .text = viewModel.project.value?.path?.path.toString()

            refresh.apply {
                setOnRefreshListener {
                    refreshFileList()
                }
                setColorSchemeColors(requireContext().getAttributeColor(R.attr.colorPrimary))
            }
        }




        viewModel.rootNode.observe(viewLifecycleOwner) {

            treeView.rootNode.children.clear()
            treeView.rootNode.addChild(it)
            treeView.refreshTreeView()
        }

        viewModel.refreshFileList()

    }

    private fun refreshFileList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewBinding.refresh.isRefreshing = true
            val node = withContext(Dispatchers.IO) {
                TreeHelper.updateNode(viewModel.rootNode.value)
            }

            viewModel.rootNode.value = node
            viewBinding.refresh.isRefreshing = false
        }
    }


}