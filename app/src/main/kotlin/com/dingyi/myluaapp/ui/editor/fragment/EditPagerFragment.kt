package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dingyi.editor.data.ColumnNavigationItem
import com.dingyi.editor.language.java.JavaLanguage
import com.dingyi.editor.language.lua.LuaLanguage
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.endsWith
import com.dingyi.myluaapp.common.kts.javaClass
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.presenter.EditPagerPresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import livedatabus.LiveDataBus

/**
 * @author: dingyi
 * @date: 2021/8/8 22:57
 * @description:
 **/
class EditPagerFragment : BaseFragment<FragmentEditorEditPagerBinding, MainViewModel>() {

    var openPath = ""

    lateinit var codeFile: CodeFile

    val codeEditor by lazy(LazyThreadSafetyMode.NONE) {
        viewBinding.codeEditor
    }


    private val presenter by lazy(LazyThreadSafetyMode.NONE) {
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
                        codeEditor.cursor.onCommitText(it.second)
                    }
                }
            }


        LiveDataBus
            .getDefault()
            .with("undo", javaClass<Pair<Int, String>>())
            .observe(viewLifecycleOwner) {
                viewModel.projectConfig.value?.let { config ->
                    val position = config.findCodeFileByPath(openPath)
                    if (position == it.first) {
                        codeEditor.undo()
                    }
                }
            }

        LiveDataBus
            .getDefault()
            .with("redo", javaClass<Pair<Int, String>>())
            .observe(viewLifecycleOwner) {
                viewModel.projectConfig.value?.let { config ->
                    val position = config.findCodeFileByPath(openPath)
                    if (position == it.first) {
                        codeEditor.redo()
                    }
                }
            }

        viewModel.realPosition.observe(viewLifecycleOwner) {
            observeAndSendNavigationList()
        }

    }

    private fun observeAndSendNavigationList() {
        viewModel.projectConfig.value?.let { config ->
            val position = config.findCodeFileByPath(openPath)
            val realPosition = viewModel.realPosition.value
            if (position == realPosition) {
                codeEditor.textAnalyzeResult.navigation?.map {
                    it as ColumnNavigationItem
                }?.let {
                    if (viewModel.navigationList.value?.toString() != it.toString()) {
                        viewModel.navigationList.postValue(it)
                    }
                }
            }
        }
    }

    private fun initEditor() {
        codeEditor.apply {
            when {
                openPath.endsWith(".lua", ".aly") -> {
                    setEditorLanguage(LuaLanguage(viewBinding.codeEditor))
                }
                openPath.endsWith(".java") -> {
                    setEditorLanguage(JavaLanguage(viewBinding.codeEditor))
                }
            }
            this.isNestedScrollingEnabled
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    delay(150)
                    presenter.saveCodeFile(true)
                    presenter.checkFile()
                    observeAndSendNavigationList()
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

