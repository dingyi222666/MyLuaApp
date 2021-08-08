package com.dingyi.MyLuaApp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.common.helper.PermissionHelper
import com.dingyi.MyLuaApp.common.kts.getAttributeColor
import com.dingyi.MyLuaApp.common.kts.iconColor
import com.dingyi.MyLuaApp.common.kts.startActivity
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding
import com.dingyi.MyLuaApp.ui.adapter.MainProjectListAdapter
import com.dingyi.MyLuaApp.ui.editor.EditorActivity
import com.dingyi.MyLuaApp.ui.newProject.NewProjectActivity

/**
 * @author: dingyi
 * @date: 2021/8/4 15:06
 * @description:
 **/
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter>() {

    private val mainProjectAdapter = MainProjectListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }


        PermissionHelper(this).requestExternalStoragePermission {
            initView()
            presenter.requestPoetry()
            presenter.refreshProjectList()
        }
    }

    private fun initView() {
        viewBinding.apply {
            list.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = mainProjectAdapter
            }

        }
        mainProjectAdapter.setOnClickListener {
            startActivity<EditorActivity> {
                putExtra("info", it)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.refreshProjectList()
    }

    override fun observeViewModel() {

        viewModel.apply {
            title.observe(this@MainActivity) {
                supportActionBar?.subtitle = it
            }
            mainProjectList.observe(this@MainActivity) {
                mainProjectAdapter.submitList(it)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_action_menu_new_project -> {
                startActivity<NewProjectActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java

    }

    override fun getViewBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel, this)
    }
}