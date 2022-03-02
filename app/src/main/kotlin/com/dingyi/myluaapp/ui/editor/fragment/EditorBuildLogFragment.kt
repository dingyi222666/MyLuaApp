package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.core.log.listener.ApkInstaller
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

    private var isCompleted = false

    private val callback = { it: LogBroadcastReceiver.Log ->
        if (isCompleted) {
            viewBinding.logView.clear()
            isCompleted = false
        }
        if (logReceiver.isCompleted()) {
            isCompleted = true
        }
        if (it.message != "BUILD COMPLETED FLAG") {
            viewBinding
                .logView
                .sendLog(it.level, it.message, it.extra)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logReceiver = viewModel.logBroadcastReceiver.value.checkNotNull()

        logReceiver.addCallback(callback)

        viewBinding.logView
            .addLogListener(ApkInstaller(requireContext()))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        logReceiver.removeCallback(callback)
    }


}