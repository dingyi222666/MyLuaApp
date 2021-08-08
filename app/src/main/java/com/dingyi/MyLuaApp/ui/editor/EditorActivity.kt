package com.dingyi.MyLuaApp.ui.editor

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.common.kts.AdapterDrawerListener
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/8 15:24
 * @description:
 **/
class EditorActivity : BaseActivity<ActivityEditorBinding, MainViewModel, MainPresenter>() {

    private var projectInfo by Delegates.notNull<ProjectInfo>()

    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<ProjectInfo>("info")?.let {
            projectInfo = it
            viewModel.projectInfo.value=it
        }

        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar?.apply {
            title = projectInfo.appName
            setDisplayHomeAsUpEnabled(true)
        }


        viewBinding.apply {
            val actionBarDrawerToggle = ActionBarDrawerToggle(this@EditorActivity, drawer, 0, 0)

            drawer.apply {
                addDrawerListener(actionBarDrawerToggle)
                addDrawerListener(AdapterDrawerListener(
                    _onDrawerClosed = {
                        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    },
                    _onDrawerOpened = {
                        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                    }
                ))
            }
            actionBarDrawerToggle.syncState()

        }



    }

    override fun observeViewModel() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                viewBinding.drawer.apply {
                    if (isDrawerOpen(GravityCompat.START))
                        closeDrawer(GravityCompat.START)
                    else
                        openDrawer(GravityCompat.START)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }
}