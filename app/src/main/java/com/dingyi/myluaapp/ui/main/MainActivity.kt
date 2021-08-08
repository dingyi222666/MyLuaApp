package com.dingyi.myluaapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.helper.PermissionHelper
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.iconColor
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.ui.adapter.MainProjectListAdapter
import com.dingyi.myluaapp.ui.editor.EditorActivity
import com.dingyi.myluaapp.ui.newproject.NewProjectActivity

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