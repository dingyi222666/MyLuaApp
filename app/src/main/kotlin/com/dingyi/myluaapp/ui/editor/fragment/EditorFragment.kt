package com.dingyi.myluaapp.ui.editor.fragment


import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
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
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
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


    private lateinit var editor: Editor<EditorState>

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

    private fun initEditor() {


        viewBinding.codeEditor.apply {
            isMagnifierEnabled =
                PreferenceManager.getDefaultSharedPreferences(requireActivity()).getBoolean(
                    "magnifier_set", true
                )
        }

        val path = arguments?.getString("editor_page_path", "") ?: ""

        editor = (PluginModule
            .getEditorService()
            .getEditor(path.toFile())
            ?: error("Unable to get editor for path:$path")) as Editor<EditorState>


        editor.binCurrentView(viewBinding.codeEditor)


        editor.read()



    }


    override fun onDestroy() {
        super.onDestroy()

        val path = arguments?.getString("editor_page_path", "") ?: ""

        editor.save()

    }


}
