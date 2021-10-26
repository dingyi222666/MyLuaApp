package com.dingyi.myluaapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.iconColor
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.ui.main.model.ProjectUiModel
import com.dingyi.myluaapp.ui.newproject.NewProjectActivity
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2021/10/22 23:36
 * @description:
 **/
class MainActivity : BaseActivity<
        ActivityMainBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.projectList.observe(this) { projectList ->
            projectList.map {
                ProjectUiModel(it)
            }.let {
                viewBinding.list.bindingAdapter
                    .models = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }


        initViewBinding()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.refreshProjectList(viewBinding) //立即刷新一次
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.refreshProjectList(viewBinding)
            }
        }
    }

    private fun initViewBinding() {
        viewBinding.list
            .linear(orientation = LinearLayoutManager.VERTICAL)
            .setup {
                addType<ProjectUiModel>(R.layout.layout_item_main_project)
            }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.main_action_menu_new_project -> {
                startActivity<NewProjectActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
    }

}