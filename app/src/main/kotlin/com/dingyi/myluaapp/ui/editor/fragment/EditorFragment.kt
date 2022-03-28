package com.dingyi.myluaapp.ui.editor.fragment


import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.databinding.FragmentEditorEditPagerBinding
import com.dingyi.myluaapp.editor.ktx.getComponent
import com.dingyi.myluaapp.ui.editor.MainViewModel
import io.github.rosemoe.sora.widget.component.Magnifier
import kotlinx.coroutines.launch

/**
 * @email : dingyi222666@foxmail.com
 * @author : dingyi
 * @time : 2021/11/30 16:26
 * @description : A Editor Fragment
 */
class EditorFragment : BaseFragment<FragmentEditorEditPagerBinding, MainViewModel>() {


    private lateinit var editor: Editor

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

    }

    override fun onResume() {
        super.onResume()
        editor.binCurrentView(viewBinding.codeEditor)
    }


    private fun initEditor() {


        viewBinding.codeEditor.apply {

            getComponent<Magnifier>()
                .isEnabled =
                PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(
                    "magnifier_set", true
                )
        }

        val path = arguments?.getString("editor_page_path", "") ?: ""

        editor = (PluginModule
            .getEditorService()
            .getEditor(path.toFile())
            ?: error("Unable to get editor for path:$path"))


        editor.binCurrentView(viewBinding.codeEditor)


        lifecycleScope.launch {
            runCatching {
                editor.read()
            }.onFailure {
                Log.e("EditorFragment", "Unable to read file:${it.message}",it)
                System.err.println(it)
                closeAndRefreshEditor(editor)
            }
        }


    }


    private fun closeAndRefreshEditor(editor: Editor) {
        PluginModule
            .getEditorService()
            .closeEditor(editor)

        viewModel
            .refreshEditor()
    }

    override fun onDestroy() {
        super.onDestroy()

        runCatching {
            editor.save()
        }.onFailure {
            System.err.println(it)
            closeAndRefreshEditor(editor)
        }

    }


}
