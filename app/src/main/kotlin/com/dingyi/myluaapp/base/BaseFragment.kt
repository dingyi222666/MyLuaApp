package com.dingyi.myluaapp.base

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showSnackBar
import com.hjq.language.MultiLanguages

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


        viewModel = ViewModelProvider(requireActivity())[getViewModelClass()]


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getViewBindingImp(inflater, container).root
    }


    private fun getViewBindingImp(inflater: LayoutInflater, container: ViewGroup?): V {
        if (!this::viewBinding.isInitialized) {
            viewBinding = getViewBindingInstance(inflater, container)
        }
        return viewBinding
    }


    abstract fun getViewModelClass(): Class<T>

    abstract fun getViewBindingInstance(inflater: LayoutInflater, container: ViewGroup?): V


    companion object {
        fun newInstance(bundle: Bundle): Fragment? {
            return null
        }
    }





}