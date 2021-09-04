package com.dingyi.myluaapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.ui.GeneralActivity
import com.dingyi.myluaapp.ui.about.AboutActivity
import com.dingyi.myluaapp.ui.editor.EditorActivity
import com.dingyi.myluaapp.ui.main.adapter.ProjectListAdapter
import com.dingyi.myluaapp.ui.newproject.NewProjectActivity
import com.dingyi.myluaapp.ui.settings.SettingsFragment

/**
 * @author: dingyi
 * @date: 2021/8/4 15:06
 * @description:
 **/
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter>() {

    private val mainProjectAdapter = ProjectListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }



        initView()
        presenter.requestPoetry()
        presenter.refreshProjectList()

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
            R.id.main_action_menu_about -> startActivity<AboutActivity>()
            R.id.main_action_menu_settings -> {
                startActivity<GeneralActivity> {
                    putExtra("type", javaClass<SettingsFragment>().name)
                    val targetBundle=Bundle()
                    targetBundle.putInt("resId",R.xml.settings_main)
                    putExtra("arg",targetBundle)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return javaClass()

    }

    override fun getViewBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel, this)
    }
}