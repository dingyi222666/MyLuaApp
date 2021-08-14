package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dingyi.editor.BaseEditor
import com.dingyi.editor.lua.LuaEditor
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.javaClass
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.presenter.EditPagerPresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import livedatabus.LiveDataBus
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/8 22:57
 * @description:
 **/
class EditPagerFragment : BaseFragment<FragmentEditorEditPagerBinding, MainViewModel>() {

    var openPath = ""

    lateinit var codeFile: CodeFile

    var editorView by Delegates.notNull<BaseEditor>()


    private val presenter by lazy {
        EditPagerPresenter(this, viewModel)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }


    companion object {
        fun newInstance(bundle: Bundle?): EditPagerFragment {
            return EditPagerFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openPath = arguments?.getString("path").toString()

        initEditor()

        viewBinding.root.addView(editorView, -1, -1)
        presenter.readCodeFile()
        initLiveDataBus()
    }

    private fun initLiveDataBus() {
        LiveDataBus
            .getDefault()
            .with("saveAllFile", String::class.java)
            .observe(viewLifecycleOwner) {
                if (::codeFile.isInitialized) {
                    presenter.checkFile()
                }
            }

        LiveDataBus
            .getDefault()
            .with("addSymbol", javaClass<Pair<Int, String>>())
            .observe(viewLifecycleOwner) {
                viewModel.projectConfig.value?.let { config ->
                    val position = config.findCodeFileByPath(openPath)
                    if (position == it.first) {
                        editorView.paste(it.second)
                    }
                }
            }

    }


    private fun initEditor() {
        editorView = when {
            openPath.endsWith(".lua") -> {
                LuaEditor(requireContext())
            }
            else -> {
                LuaEditor(requireContext())
            }
        }



        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    delay(150)
                    presenter.saveCodeFile(true)
                    presenter.checkFile()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        presenter.saveCodeFile(true)
        presenter.checkFile()

    }


    override fun onResume() {
        super.onResume()


        if (!::codeFile.isInitialized) {
            presenter.readCodeFile()
        } else {
            presenter.readCodeFile(true)
        }
        presenter.checkFile()

    }

    override fun onDestroyView() {
        presenter.saveCodeFile(true)
        presenter.checkFile()
        super.onDestroyView()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorEditPagerBinding {
        return FragmentEditorEditPagerBinding.inflate(inflater, container, false)
    }


}

