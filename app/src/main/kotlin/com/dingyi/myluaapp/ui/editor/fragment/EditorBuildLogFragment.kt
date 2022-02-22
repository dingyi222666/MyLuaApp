package com.dingyi.myluaapp.ui.editor.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.databinding.FragmentEditorBuildLogBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel

class EditorBuildLogFragment : BaseFragment<FragmentEditorBuildLogBinding, MainViewModel>() {

    private lateinit var logReceiver: LogBroadcastReceiver

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    private var buildEndFlag = false

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorBuildLogBinding {
        return FragmentEditorBuildLogBinding.inflate(inflater, container, false)
    }

    private val callback = { it: LogBroadcastReceiver.Log ->
        if (it.message == "BUILD END FLAG") {
            buildEndFlag = true
            System.gc()
        }
        if (it.message != "BUILD END FLAG" && buildEndFlag) {
            viewBinding.logView.clear()
            logReceiver.clear()
            buildEndFlag = false
        }
        if (it.message != "BUILD END FLAG") {
            viewBinding
                .logView
                .sendLog(it.level, it.message)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        logReceiver = viewModel.logBroadcastReceiver.value.checkNotNull()

        logReceiver.addCallback(callback)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        logReceiver.removeCallback(callback)
    }


}