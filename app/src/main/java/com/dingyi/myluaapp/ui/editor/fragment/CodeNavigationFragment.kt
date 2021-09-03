package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.editor.struct.ColumnNavigationItem
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.javaClass
import com.dingyi.myluaapp.databinding.FragmentEditorCodeNavigationBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.adapter.CodeNavigationAdapter

/**
 * @author: dingyi
 * @date: 2021/9/3 19:29
 * @description:
 **/
class CodeNavigationFragment : BaseFragment<FragmentEditorCodeNavigationBinding, MainViewModel>() {

    private val adapter by lazy {
        CodeNavigationAdapter()
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return javaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorCodeNavigationBinding {
        return FragmentEditorCodeNavigationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.navigationList.value?.let { list ->
            submitListToAdapter(list)
        }
        viewModel.navigationList.observe(viewLifecycleOwner) { it ->
            submitListToAdapter(it)
        }
    }

    private fun submitListToAdapter(list: List<ColumnNavigationItem>) {
        list.map {
            "(${it.line},${it.column}) --${it.type} ${it.label}"
        }.apply {
            adapter.submitList(this)
        }
    }

    private fun initView() {
        viewBinding.apply {
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

    }
}