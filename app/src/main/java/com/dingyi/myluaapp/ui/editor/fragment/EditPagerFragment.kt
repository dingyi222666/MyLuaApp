package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dingyi.editor.BaseEditor
import com.dingyi.editor.lua.LuaEditor
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.presenter.EditPagerPresenter
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

    val fileIsInitialized = ::codeFile.isInitialized

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


        editorView.setOnTextChangeListener {
            presenter.checkFile()
        }

    }


    override fun onResume() {
        super.onResume()
        presenter.readCodeFile()
    }

    override fun onPause() {
        super.onPause()
        presenter.saveCodeFile()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorEditPagerBinding {
        return FragmentEditorEditPagerBinding.inflate(inflater, container, false)
    }


}