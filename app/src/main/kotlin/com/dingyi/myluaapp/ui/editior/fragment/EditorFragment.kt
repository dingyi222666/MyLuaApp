package com.dingyi.myluaapp.ui.editior.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editior.MainViewModel

/**
 * @email : dingyi222666@foxmail.com
 * @author : dingyi
 * @time : 2021/11/30 16:26
 * @description : A Editor Fragment
 */
class EditorFragment: BaseFragment<FragmentEditorEditPagerBinding,MainViewModel>() {



    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorEditPagerBinding {
        return FragmentEditorEditPagerBinding.inflate(inflater,container,false)
    }


    companion object {
        fun newInstance(bundle: Bundle):EditorFragment {
            return  EditorFragment().apply {
                arguments = bundle
            }
        }
    }

}