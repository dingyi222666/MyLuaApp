package com.dingyi.myluaapp.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dingyi.editor.BaseEditor
import com.dingyi.editor.lua.LuaEditor
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.database.bean.CodeFile
import com.dingyi.myluaapp.database.service.ProjectService
import com.dingyi.myluaapp.databinding.FragmentEditorPagerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/8 22:57
 * @description:
 **/
class EditorPagerFragment : BaseFragment<FragmentEditorPagerBinding, MainViewModel>() {

    private var path = ""

    private lateinit var file: CodeFile

    private var position = 0

    private var editor by Delegates.notNull<BaseEditor>()

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }


    companion object {
        fun newInstance(bundle: Bundle?): EditorPagerFragment {
            return EditorPagerFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = arguments?.getString("path").toString()

        position = arguments?.getInt("position", 0) ?: 0

        initEditor()

        viewBinding.root.addView(editor, -1, -1)

        if (viewModel.editPagerTabText.size<=position) {
            viewModel.editPagerTabText.add(position, MutableLiveData(path.toFile().name))
        } else {
            viewModel.editPagerTabText[position] = MutableLiveData(path.toFile().name)
        }

    }


    private fun readCodeFile() {
        if (!::file.isInitialized) {
            lifecycleScope.launch {
                ProjectService.queryCodeFile(path)?.let {
                    file = it
                    editor.text = it.path.toFile().readText()
                    editor.setSelection(it.selection)
                }
            }
        }

    }

    private fun saveCodeFile(
        saveText: String = editor.text.toString()
    ) {
        lifecycleScope.launch {
            file.apply {
                selection = editor.selectionEnd
            }
            withContext(Dispatchers.IO) {
                file.path.toFile().writeText(saveText)
                file.update(file.id.toLong())
            }

        }
    }

    private fun initEditor() {
        editor = when {
            path.endsWith(".lua") -> {
                LuaEditor(requireContext())
            }
            else -> {
                LuaEditor(requireContext())
            }
        }

        editor.setOnTextChangeListener {
            lifecycleScope.launch {
                val text = withContext(Dispatchers.IO) {
                    file.path.toFile().readText()
                }
                if (text != editor.text) {
                    viewModel.editPagerTabText[position].value = "*" + file.path.toFile().name
                } else {
                    viewModel.editPagerTabText[position].value = file.path.toFile().name
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        readCodeFile()
    }

    override fun onPause() {
        super.onPause()
        saveCodeFile()
    }


    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorPagerBinding {
        return FragmentEditorPagerBinding.inflate(inflater, container, false)
    }

}