package com.dingyi.myluaapp.ui.editor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dingyi.editor.language.lua.LuaLanguage
import com.dingyi.editor.scheme.SchemeLua
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.endsWith
import com.dingyi.myluaapp.common.kts.javaClass
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.presenter.EditPagerPresenter
import io.github.rosemoe.editor.widget.schemes.SchemeEclipse
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

    val codeEditor by lazy {
        viewBinding.codeEditor
    }


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

    }


    private fun initEditor() {
        codeEditor.apply {
            when {
                openPath.endsWith(".lua", ".aly") -> {
                    setEditorLanguage(LuaLanguage())
                }
            }
            colorScheme=SchemeLua
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

