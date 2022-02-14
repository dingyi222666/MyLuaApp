package com.dingyi.myluaapp.ui.editor.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.getPrivateField
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.launch

class EditorActivity : BaseActivity<ActivityEditorBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        //先不启用过渡动画
        postponeEnterTransition()

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.openProject((intent.getStringExtra("project_path") ?: "").toFile())
        }
        setSupportActionBar(viewBinding.toolbar)


        //反射获取控件和启用过渡动画
        viewBinding.toolbar.getPrivateField<TextView>("mTitleTextView").apply {
            transitionName =
                "project_name_transition"

            ellipsize = TextUtils.TruncateAt.END

        }

        startPostponedEnterTransition()


    }
}