package com.dingyi.myluaapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.startActivity
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.ui.GeneralActivity
import com.dingyi.myluaapp.ui.about.AboutFragment
import com.dingyi.myluaapp.ui.settings.SettingsFragment
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        viewModel.refreshPoetry(this)

        initViewBinding()
        initData()


    }



    private fun initViewBinding() {
       /* viewBinding.list
            .linear(orientation = LinearLayoutManager.VERTICAL)
            .setup {
                addType<ProjectUiModel>(R.layout.layout_item_main_project)
            }
            .onBind {
                itemView.setOnClickListener {
                    val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        LayoutItemMainProjectBinding.bind(itemView).appName,
                        "project_name_transition"
                    ).toBundle()

                    this@MainActivity.startActivity(
                        Intent(this@MainActivity, getJavaClass<EditorActivity>()).apply {
                            putExtra(
                                "project_path",
                                this@onBind.getModel<ProjectUiModel>().project.path.path
                            )
                        }, bundle
                    )

                }
            }*/
    }

    override fun observeViewModel() {
        super.observeViewModel()


        viewModel.poetry.observe(this) {
            supportActionBar?.subtitle = it
        }


    }



    private fun initData() {
        lifecycleScope.launch {
            //viewModel.refreshProjectList(viewBinding) //立即刷新一次
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.refreshProjectList(viewBinding)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_action_menu_new_project -> {
             //   startActivity<NewProjectActivity>()
            }
            R.id.main_action_menu_settings -> {
                startActivity<GeneralActivity> {
                    putExtra("type", getJavaClass<SettingsFragment>().name)
                    putExtra(
                        "arg", Bundle()
                    )
                }
            }
            R.id.main_action_menu_about -> {
                startActivity<GeneralActivity> {
                    putExtra("type", getJavaClass<AboutFragment>().name)
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}