package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.core.project.ProjectFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import kotlin.math.absoluteValue

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

                withContext(Dispatchers.Default) {
                    readEditorData(
                        getProjectFile().readEditorData()
                    )
                }

            }.onFailure {
                it.printStackTrace()
            }
        }


    }

    private fun initEditor() {

        viewBinding.codeEditor.apply {
            isMagnifierEnabled =
                PreferenceManager.getDefaultSharedPreferences(requireActivity()).getBoolean(
                    "magnifier_set", true
                )
        }

        addCodeEditorListener()
    }

    private fun getProjectFile(): ProjectFile {
        return projectFile?.get() ?: ProjectFile(
            arguments?.getString("editor_page_path", "") ?: "",
            viewModel.controller.getProject()
        ).apply {
            //被回收的话就重新给一个值
            projectFile = WeakReference(this)
        }
    }


    private fun createEditorData(): Map<String, Float> {
        return mapOf(
            "line" to viewBinding.codeEditor.text.cursor.leftLine.toFloat(),
            "scrollX" to viewBinding.codeEditor.scroller.currX.toFloat(),
            "scrollY" to viewBinding.codeEditor.scroller.currY.toFloat(),
            "column" to viewBinding.codeEditor.text.cursor.leftColumn.toFloat(),
            "textSize" to viewBinding.codeEditor.textSizePx
        )
    }

    private fun readEditorData(data: Map<String, Float>) {
        requireActivity().runOnUiThread {
            viewBinding.codeEditor.apply {
                val textSize = data.getValue("textSize")
                if (textSize > 0) {
                    textSizePx = textSize
                }
                handler.postDelayed(0) {
                    scroller.startScroll(
                        scroller.currX,
                        scroller.currY,
                        data.getValue("scrollX").toInt() - scroller.currX,
                        data.getValue("scrollY").toInt() - scroller.currY, 0
                    )
                }
                text.cursor.set(
                    data.getValue("line").toInt(),
                    data.getValue("column").toInt()
                )

            }

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
                            getProjectFile().commitChange(
                                viewBinding.codeEditor.text,
                                createEditorData()
                            )
                        }
                    }
                    lastCodeContentLength = viewBinding.codeEditor.text.length
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        val path = arguments?.getString("editor_page_path", "") ?: ""

        //先清空旧的
        projectFile?.clear()

        //如果没有虚拟文件引用 就是彻底删除了 这时候就不会自动保存
        if (ProjectFile.checkVirtualProjectPathExists(
                viewModel.controller.getProject().projectPath,
                path
            )
        ) {
            //新获取一个
            getProjectFile().apply {
                commitChange(viewBinding.codeEditor.text, createEditorData())
                saveChange()
            }
        }
        //清除引用
        projectFile?.clear()

    }


}