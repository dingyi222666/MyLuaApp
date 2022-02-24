package com.dingyi.myluaapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.databinding.LayoutItemMainProjectBinding
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.activity.EditorActivity
import com.dingyi.myluaapp.ui.main.model.ProjectUiModel
import com.dingyi.myluaapp.ui.newproject.NewProjectActivity
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        PluginModule
            .init()
        PluginModule
            .loadAllPlugin()
    }

    private fun initViewBinding() {
        viewBinding.list
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
            }
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

        viewModel.poetry.observe(this) {
            supportActionBar?.subtitle = it
        }

    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.refreshProjectList(viewBinding) //立即刷新一次
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.refreshProjectList(viewBinding)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_action_menu_new_project -> {
                startActivity<NewProjectActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        PluginModule.stop()
    }
}