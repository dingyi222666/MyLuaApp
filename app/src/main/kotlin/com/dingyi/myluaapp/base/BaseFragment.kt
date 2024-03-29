package com.dingyi.myluaapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author: dingyi
 * @date: 2021/8/4 21:55
 * @description:
 **/
abstract class BaseFragment<V : ViewBinding, T : ViewModel> : Fragment() {


    protected lateinit var viewBinding: V

    protected lateinit var viewModel: T


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity(),ViewModelProvider.NewInstanceFactory.instance)[getViewModelClass()]


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getViewBindingImp(inflater, container).root.apply {
            layoutParams =
                LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }


    private fun getViewBindingImp(inflater: LayoutInflater, container: ViewGroup?): V {
        if (!this::viewBinding.isInitialized) {
            viewBinding = getViewBindingInstance(inflater, container)
        }
        return viewBinding
    }


    abstract fun getViewModelClass(): Class<T>

    abstract fun getViewBindingInstance(inflater: LayoutInflater, container: ViewGroup?): V




}