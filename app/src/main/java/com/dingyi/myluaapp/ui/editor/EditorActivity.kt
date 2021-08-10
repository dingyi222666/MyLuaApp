package com.dingyi.myluaapp.ui.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.adapter.EditorPagerAdapter
import com.dingyi.myluaapp.ui.editor.presenter.MainPresenter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            viewModel.projectInfo.value = it
        }

        setSupportActionBar(viewBinding.toolbar)

        supportActionBar?.apply {
            title = projectInfo.appName
            setDisplayHomeAsUpEnabled(true)
        }



        viewBinding.apply {
            val actionBarDrawerToggle = ActionBarDrawerToggle(this@EditorActivity, drawer, 0, 0)

            drawer.apply {
                addDrawerListener(actionBarDrawerToggle)
                addDrawerListener(
                    onDrawerClosed = {
                        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    },
                    onDrawerOpened = {
                        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                    }
                )
            }
            actionBarDrawerToggle.syncState()

            editorPage.apply {
                adapter = EditorPagerAdapter(this@EditorActivity, viewModel)
                isUserInputEnabled = false
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        viewModel.projectConfig.value?.run {
                            val name = openFiles.run {
                                get(position).path.toFile().name
                            }.toString()
                            supportActionBar?.subtitle = name
                        }
                    }
                })
            }

            arrayOf(main).forEach {
                it.addLayoutTransition()
            }


            TabLayoutMediator(
                editorTab,
                editorPage, true, true
            ) { tab, position ->
                if (viewModel.editPagerTabText.size<position+1) {
                    viewModel.projectConfig.value?.run {
                        val name = openFiles.run {
                            get(position).path.toFile().name
                        }.toString()
                        viewModel.editPagerTabText.add(position, MutableLiveData(name))
                    }
                }

                viewModel.editPagerTabText[position]?.let { liveData ->
                    if (tab.text != liveData.value) {
                        tab.text = liveData.value
                    }
                    liveData.removeObservers(this@EditorActivity)
                    liveData.observe(this@EditorActivity) {
                        tab.text = it
                    }
                }

            }.attach()

        }

        presenter.openProject(projectInfo)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeViewModel() {
        viewModel.apply {
            projectConfig.observe(this@EditorActivity) {
                lifecycleScope.launch {
                    delay(400)
                    viewBinding.let {
                        if (it.progress.visibility == View.VISIBLE) {
                            it.progress.visibility = View.GONE
                            it.editorPage.visibility = View.VISIBLE

                        }
                    }
                    println(it)
                    (viewBinding.editorPage.adapter as EditorPagerAdapter)
                        .notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_toolbar,menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
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