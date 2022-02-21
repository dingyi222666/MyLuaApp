package com.dingyi.myluaapp.ui.editor.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.databinding.FragmentEditorBuildLogBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel

class EditorBuildLogFragment : BaseFragment<FragmentEditorBuildLogBinding, MainViewModel>() {

    private lateinit var logReceiver: LogBroadcastReceiver

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorBuildLogBinding {
        return FragmentEditorBuildLogBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        logReceiver = LogBroadcastReceiver(viewLifecycleOwner.lifecycle, requireActivity())

        logReceiver.addCallback {

            viewBinding
                .logView
                .sendLog(it.getStringExtra("tag").toString(),it.getStringExtra("message").toString())
        }

    }


}