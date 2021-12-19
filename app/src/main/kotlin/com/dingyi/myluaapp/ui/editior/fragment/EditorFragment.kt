package com.dingyi.myluaapp.ui.editior.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.core.project.ProjectFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editior.MainViewModel
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

/**
 * @email : dingyi222666@foxmail.com
 * @author : dingyi
 * @time : 2021/11/30 16:26
 * @description : A Editor Fragment
 */
class EditorFragment : BaseFragment<FragmentEditorEditPagerBinding, MainViewModel>() {

    private var projectFile: WeakReference<ProjectFile>? = null

    private var lastCodeContentLength = 0

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorEditPagerBinding {
        return FragmentEditorEditPagerBinding.inflate(inflater, container, false)
    }


    companion object {
        fun newInstance(bundle: Bundle): EditorFragment {
            return EditorFragment().apply {
                arguments = bundle
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initEditor()

        lifecycleScope.launch {
            runCatching {
                viewBinding.codeEditor.setText(
                    withContext(Dispatchers.IO) { getProjectFile().readText() })
            }.onFailure {
                it.printStackTrace()
            }
        }



    }

    private fun initEditor() {

        viewBinding.codeEditor.apply {
            isMagnifierEnabled = PreferenceManager.getDefaultSharedPreferences(requireActivity()).getBoolean(
                "magnifier_set",true
            )
        }

        addCodeEditorListener()
    }

    private fun getProjectFile(): ProjectFile {
        return projectFile?.get() ?: ProjectFile(
            arguments?.getString("editor_page_path", "") ?: "",
            viewModel.controller.getProjectFile()
        ).apply {
            //被回收的话就重新给一个值
            projectFile = WeakReference(this)
        }
    }


    private fun addCodeEditorListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    delay(1000 * 30) //休眠30秒
                    withContext(Dispatchers.Default) {

                        val nowCodeContentLength = viewBinding.codeEditor.text.length
                        if ((nowCodeContentLength - lastCodeContentLength).absoluteValue > 20) {
                            Log.d("test", "commit change!")
                            getProjectFile().commitChange(viewBinding.codeEditor.text)
                        }
                    }
                    lastCodeContentLength = viewBinding.codeEditor.text.length
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //先清空旧的
        projectFile?.clear()
        //新获取一个
        getProjectFile().apply {
            commitChange(viewBinding.codeEditor.text)
            saveChange()
        }
        //清除引用
        projectFile?.clear()

    }


}