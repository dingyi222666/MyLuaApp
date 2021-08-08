package com.dingyi.myluaapp.ui.newproject

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.ActivityNewProjectBinding
import com.google.android.material.chip.Chip

/**
 * @author: dingyi
 * @date: 2021/8/4 22:08
 * @description:
 **/
class NewProjectActivity : BaseActivity<ActivityNewProjectBinding, MainViewModel, MainPresenter>() {




    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel, this)
    }

    override fun observeViewModel() {


        viewModel.apply {
            templates.observe(this@NewProjectActivity) { templates ->
                templates.templates.forEachIndexed { index, it ->
                    val name = it.name
                    viewBinding.chipGroup.addView(
                        Chip(this@NewProjectActivity).apply {
                            text = name
                            id = index + 1
                            isCheckable = true
                        }
                    )
                }
                val chip = viewBinding.chipGroup[0] as Chip
                chip.isChecked = true

            }


            showProgressBar.observe(this@NewProjectActivity) {
                viewBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
                optionsMenu?.let { menu ->
                    menu.findItem(R.id.new_project_action_menu_success).isEnabled=!it
                }
            }

            appName.observe(this@NewProjectActivity) {
                viewBinding.appName.setTextIfDiff(it)
                info.appName = it
                viewBinding.appNameParent.isErrorEnabled = true
                if (it.isEmpty()) {
                    viewBinding.appNameParent.error =
                        getString(R.string.new_project_app_name_error_empty)
                } else if (presenter.checkAppNameCanUse(it)) {
                    viewBinding.appNameParent.error = getString(R.string.new_project_app_name_error)
                } else {
                    viewBinding.appNameParent.isErrorEnabled = false
                }
            }

            appPackageName.observe(this@NewProjectActivity) {
                info.appPackageName = it
                viewBinding.appPackageName.setTextIfDiff(it)
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
                        viewModel.info.template = viewModel.templates.value?.templates?.get(id - 1)
                    }
                    lastSelectId = if (id == -1) lastSelectId else id
                }
            };

            arrayOf(root,chipGroup).forEach {
              it.addLayoutTransition()
            }

            appName.bindTextChangedToLiveData(viewModel.appName)
            appPackageName.bindTextChangedToLiveData(viewModel.appPackageName)

        }

        presenter.updateTemplates()
        presenter.initDefaultAppName()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.new_project_action_menu_success -> presenter.newProject()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_project_toolbar, menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getViewBindingInstance(): ActivityNewProjectBinding {
        return ActivityNewProjectBinding.inflate(layoutInflater)
    }
}