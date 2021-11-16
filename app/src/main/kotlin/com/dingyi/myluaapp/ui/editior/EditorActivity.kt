package com.dingyi.myluaapp.ui.editior

import android.os.Bundle
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.ActivityEditorBinding

/**
 * @author: dingyi
 * @date: 2021/11/3 15:38
 * @description:
 **/
class EditorActivity: BaseActivity<ActivityEditorBinding,MainViewModel>() {



    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initProjectController(intent.getStringExtra("project_path") ?: "")

    }
}