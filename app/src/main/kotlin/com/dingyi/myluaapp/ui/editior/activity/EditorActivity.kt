package com.dingyi.myluaapp.ui.editior.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.addLayoutTransition
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.getPrivateField
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editior.MainViewModel
import com.dingyi.myluaapp.ui.editior.adapter.EditorPagerAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        //先不启用过渡动画
        postponeEnterTransition()

        super.onCreate(savedInstanceState)

        viewModel.initProjectController(intent.getStringExtra("project_path") ?: "")

        initView()

        setSupportActionBar(viewBinding.toolbar)

        //反射获取控件和启用过渡动画
        viewBinding.toolbar.getPrivateField<TextView>("mTitleTextView").transitionName =
            "project_name_transition"

        startPostponedEnterTransition()

        viewModel.controller.project.openFile("build.gradle.lua")

        viewModel.refreshOpenedFile()


        lifecycleScope.launch {

            viewModel.controller.project.closeOpenedFile("build.gradle.lua", "")

            viewModel.refreshOpenedFile()

            delay(6000)

            viewModel.controller.project.openFile("build.gradle.lua")

            viewModel.refreshOpenedFile()

        }


    }


    private fun initView() {
        viewBinding.editorTab.project = viewModel.controller.project
        viewBinding.editorPage.adapter = EditorPagerAdapter(this, viewModel)
        listOf(viewBinding.container).forEach { it.addLayoutTransition() }
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
            val list = it.first
            val visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
            viewBinding.editorPage.visibility = visibility
            viewBinding.editorTab.visibility = visibility
            if (list.isNotEmpty()) viewBinding.editorTab.postOpenedFiles(list)
            viewBinding.editorPage.adapter?.notifyDataSetChanged()
        }


    }
}