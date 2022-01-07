package com.dingyi.myluaapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.dialog.builder.BottomDialogBuilder
import com.dingyi.myluaapp.common.dialog.layout.DefaultClickListLayout
import com.dingyi.myluaapp.common.dialog.layout.DefaultInputLayout
import com.dingyi.myluaapp.common.dialog.layout.DefaultMessageLayout
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showPopMenu
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.databinding.LayoutItemMainProjectBinding
import com.dingyi.myluaapp.ui.GeneralActivity
import com.dingyi.myluaapp.ui.editor.activity.EditorActivity
import com.dingyi.myluaapp.ui.main.model.ProjectUiModel
import com.dingyi.myluaapp.ui.newproject.NewProjectActivity
import com.dingyi.myluaapp.ui.settings.SettingsFragment
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        viewModel.refreshPoetry(this)

        initViewBinding()
        initData()

        test()
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

    private fun initViewBinding() {
        viewBinding.list
            .linear(orientation = LinearLayoutManager.VERTICAL)
            .setup {
                addType<ProjectUiModel>(R.layout.layout_item_main_project)
            }
            .onBind {

                itemView.setOnLongClickListener {
                    showLongClickMenu(it, this@onBind.getModel<ProjectUiModel>().project.path)
                    true
                }

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
                                this@onBind.getModel<ProjectUiModel>().project.path
                            )
                        }, bundle
                    )

                }
            }
    }


    private fun showDeleteDialog(path:String) {
        BottomDialogBuilder.with(this)
            .setTitle(R.string.main_dialog_delete_project_title)
            .setDialogLayout(DefaultMessageLayout)
            .setMessage(R.string.main_dialog_delete_project_message)
            .setPositiveButton(android.R.string.ok.getString()) { _,_ ->
                lifecycleScope.launch {
                    viewModel.deleteProject(path)
                    viewModel.refreshProjectList(viewBinding)
                }
            }
            .setNegativeButton(android.R.string.cancel.getString())
            .show()
    }

    private fun showLongClickMenu(clickView: View,path: String) {
        R.menu.main_project_list_long_click.showPopMenu(clickView) { menu ->

            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                   R.id.main_project_list_long_click_action_delete_project -> {
                       showDeleteDialog(path)
                   }
                }
                true
            }
        }
    }

    //test code here
    private fun test() {
        /*
        BottomDialogBuilder.with(this)
            .setTitle("test")
            .setPositiveButton("test")
            .setSingleChoiceItems(listOf("test" to 0, "666" to 1), 1) { _, _ -> }
            .setDialogLayout(DefaultClickListLayout())
            .show()

         */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_action_menu_new_project -> {
                startActivity<NewProjectActivity>()
                return true
            }
            R.id.main_action_menu_settings -> {
                startActivity<GeneralActivity> {
                    putExtra("type", getJavaClass<SettingsFragment>().name)
                    val targetBundle = Bundle()
                    targetBundle.putInt("resId", R.xml.settings_main)
                    putExtra("arg", targetBundle)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

}