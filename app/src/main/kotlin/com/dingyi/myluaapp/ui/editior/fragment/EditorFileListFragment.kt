package com.dingyi.myluaapp.ui.editior.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.FragmentEditorFileListBinding

import com.dingyi.myluaapp.ui.editior.MainViewModel

class EditorFileListFragment : BaseFragment<FragmentEditorFileListBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorFileListBinding {
        return FragmentEditorFileListBinding.inflate(inflater,container,false)
    }

    companion object {
        fun newInstance(bundle: Bundle): EditorFileListFragment {
            return EditorFileListFragment().apply {
                arguments = bundle
            }
        }
    }

}