package com.dingyi.myluaapp.ui.editior.activity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.addLayoutTransition
import com.dingyi.myluaapp.common.kts.addDrawerListener
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.getPrivateField
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editior.MainViewModel
import com.dingyi.myluaapp.ui.editior.adapter.EditorDrawerPagerAdapter
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

        setSupportActionBar(viewBinding.toolbar)

        initView()


        //反射获取控件和启用过渡动画
        viewBinding.toolbar.getPrivateField<TextView>("mTitleTextView").transitionName =
            "project_name_transition"

        startPostponedEnterTransition()

        viewModel.controller.project.openFile("build.gradle.lua")

        viewModel.refreshOpenedFile()


    }


    private fun initView() {
        viewBinding.editorTab.project = viewModel.controller.project
        viewBinding.editorPage.adapter = EditorPagerAdapter(this, viewModel)
        viewBinding.drawerPage.adapter = EditorDrawerPagerAdapter(this).apply {
            notifyDataSetChanged()
        }

        listOf(viewBinding.container).forEach { it.addLayoutTransition() }

        supportActionBar?.let { actionBar ->
            viewBinding.editorTab.bindActionBar(actionBar)
            actionBar.setDisplayHomeAsUpEnabled(true)
            val toggle = ActionBarDrawerToggle(this, viewBinding.drawer, viewBinding.toolbar, 0, 0)

            viewBinding.drawer.addDrawerListener(toggle)
            toggle.syncState()
        }
        viewBinding.drawer.apply {
            setScrimColor(0)
            drawerElevation = 0f
            addDrawerListener(
                onDrawerSlide = { _, slideOffset ->
                    viewBinding.main.x = viewBinding.drawerPage.width * slideOffset
                }
            )
            addDrawerListener(
                onDrawerClosed = {
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                },
                onDrawerOpened = {
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                }
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                viewBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
                if (viewBinding.drawer.isDrawerOpen(GravityCompat.START)) {
                    viewBinding.drawer.closeDrawers()
                } else {
                    viewBinding.drawer.openDrawer(GravityCompat.START)
                }
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
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
            if (list.isNotEmpty()) viewBinding.editorTab.postOpenedFiles(it.first, it.second)
            viewBinding.editorPage.adapter?.notifyDataSetChanged()
        }

    }
}