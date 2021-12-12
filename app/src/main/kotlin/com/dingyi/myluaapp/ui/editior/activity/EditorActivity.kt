package com.dingyi.myluaapp.ui.editior.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editior.MainViewModel


/**
 * @author: dingyi
 * @date: 2021/11/3 15:38
 * @description:
 **/
class EditorActivity : BaseActivity<ActivityEditorBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initProjectController(intent.getStringExtra("project_path") ?: "")

        setSupportActionBar(viewBinding.toolbar)


        initView()


    }


    private fun initView() {
        viewBinding.editorTab.project = viewModel.controller.project
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun observeViewModel() {


        viewModel.appTitle.observe(this) {
            supportActionBar?.title = it
        }

        viewModel.openFiles.observe(this) {
            viewBinding.editorTab.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            if (it.isNotEmpty()) viewBinding.editorTab.postOpenedFiles(it)
        }




    }
}