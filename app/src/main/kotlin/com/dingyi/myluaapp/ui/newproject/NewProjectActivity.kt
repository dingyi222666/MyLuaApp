package com.dingyi.myluaapp.ui.newproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.ktx.*
import com.dingyi.myluaapp.databinding.ActivityNewProjectBinding
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.google.android.material.chip.Chip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2021/10/25 19:43
 * @description:
 **/
class NewProjectActivity : BaseActivity<
        ActivityNewProjectBinding, NewProjectViewModel>() {


    private val pair by lazy(LazyThreadSafetyMode.NONE) {

        PluginModule
            .getProjectService()
            .getDefaultCreateProjectInfo()

    }

    private var selectItem: Int = 0

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.apply {
            templates.observe(this@NewProjectActivity) { templates ->
                viewBinding.chipGroup.removeAllViews()
                templates.forEachIndexed { index, it ->
                    val name = it.name
                    viewBinding.chipGroup.addView(
                        Chip(this@NewProjectActivity).apply {
                            text = name
                            id = index + 1
                            isCheckable = true
                        }
                    )
                }
                if (templates.isNotEmpty()) {
                    (viewBinding.chipGroup[0] as Chip).isChecked = true
                }
            }


            showProgressBar.observe(this@NewProjectActivity) {
                viewBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
                optionsMenu?.let { menu ->
                    menu.findItem(R.id.new_project_action_menu_success).isEnabled = !it
                }
            }

            appName.observe(this@NewProjectActivity) {
                viewBinding.appName.setTextIfDifferent(it)
                pair.first = it
                viewBinding.appNameParent.isErrorEnabled = true
                if (it.isEmpty()) {
                    viewBinding.appNameParent.error =
                        getString(R.string.new_project_app_name_error_empty)
                } else if (PluginModule
                        .getProjectService()
                        .checkCreateProjectName(pair.first)
                ) {
                    viewBinding.appNameParent.error = getString(R.string.new_project_app_name_error)
                } else {
                    viewBinding.appNameParent.isErrorEnabled = false
                }
            }



            appPackageName.observe(this@NewProjectActivity) {
                pair.second = it
                viewBinding.appPackageName.setTextIfDifferent(it)
                if (it.isEmpty()) {
                    viewBinding.appPackageNameParent.isErrorEnabled = true
                    viewBinding.appPackageNameParent.error =
                        getString(R.string.new_project_app_package_error_empty)
                } else {
                    viewBinding.appPackageNameParent.isErrorEnabled = false
                }
            }

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.new_project_action_menu_success -> lifecycleScope.launch {
                viewModel.newProject(viewModel.templates.value?.get(selectItem) ?: error(""),pair)
                delay(400)//故意延时1秒去突出新建项目的存在感
                viewModel.showProgressBar.value = false
                delay(80)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_project_toolbar, menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
    }


    override fun getViewModelClass(): Class<NewProjectViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityNewProjectBinding {
        return ActivityNewProjectBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = getString(R.string.new_project_title)
            setDisplayHomeAsUpEnabled(true)
        }


        viewBinding.apply {
            chipGroup.apply {

                var lastSelectId = chipGroup.checkedChipId
                setOnCheckedChangeListener { chip, id ->
                    if (id == -1) {
                        chip.check(lastSelectId)
                    } else {
                        selectItem = id - 1
                    }
                    lastSelectId = if (id == -1) lastSelectId else id
                }
            }

            arrayOf(root, chipGroup).forEach {
                it.addLayoutTransition()
            }

            appName.bindTextChangedToLiveData(viewModel.appName)
            appPackageName.bindTextChangedToLiveData(viewModel.appPackageName)

        }


        viewModel.appName.value = pair.first
        viewModel.appPackageName.value = pair.second

        lifecycleScope.launch {
            viewModel.getProjectTemplates()
        }
    }


}