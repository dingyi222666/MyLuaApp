package com.dingyi.myluaapp.ui.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.editor.language.java.api.AndroidApi
import com.dingyi.editor.language.lua.DrawablePool
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editor.adapter.DrawerPagerAdapter
import com.dingyi.myluaapp.ui.editor.adapter.EditPagerAdapter
import com.dingyi.myluaapp.ui.editor.fragment.FileListFragment
import com.dingyi.myluaapp.ui.editor.presenter.MainPresenter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import livedatabus.LiveDataBus
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
                adapter = EditPagerAdapter(this@EditorActivity, viewModel)
                isUserInputEnabled = false
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        viewModel.projectConfig.value?.run {
                            val name = openFiles.run {
                                get(position).filePath.toFile().name
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
                if (viewModel.editPagerTabText.size < position + 1) {
                    viewModel.projectConfig.value?.run {
                        val name = openFiles.run {
                            get(position).filePath.toFile().name
                        }.toString()
                        viewModel.editPagerTabText.add(position, MutableLiveData(name))
                    }
                }

                tab.view.setOnLongClickListener {
                    presenter.showTabMenu(tab)
                    true
                }

                viewModel.editPagerTabText[position]?.let { liveData ->
                    if (tab.text != liveData.value) {
                        tab.text = liveData.value
                    }
                    liveData.removeObservers(this@EditorActivity)
                    liveData.observe(this@EditorActivity) {
                        if (tab.text.toString()!=it) {
                            tab.text = it
                        }
                    }
                }

                symbolView.setOnClickListener {
                    LiveDataBus
                        .getDefault()
                        .with("addSymbol",javaClass<Pair<Int,String>>())
                        .value=editorPage.currentItem to it
                }

            }.attach()

        }

        presenter.openProject(projectInfo)

        AndroidApi.prepareJson(assets.open("android.json"))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeViewModel() {
        viewModel.apply {
            projectConfig.observe(this@EditorActivity) {
                lifecycleScope.launch {
                    viewBinding.run {
                        if (progress.visibility == View.VISIBLE) {
                            progress.visibility = View.GONE
                            editorPage.visibility = View.VISIBLE
                            drawerPage.apply {
                                adapter = DrawerPagerAdapter(this@EditorActivity).apply {
                                    add(FileListFragment::class.java)
                                }
                            }
                        }

                        (editorPage.adapter as EditPagerAdapter)
                            .notifyDataSetChanged()
                    }
                }
            }
        }

        LiveDataBus.getDefault()
            .with("openPath", javaClass<String>())
            .observe(this) {
                when (it.toFile().suffix) {
                    "lua" -> {
                        viewModel.projectConfig.value?.let { config ->
                            var position = config.findCodeFileByPath(it)
                            if (position == null) {
                                presenter.openFile(it)
                                position = config.findCodeFileByPath(it)
                            }
                            if (position != null) {
                                viewBinding.editorPage.currentItem = position
                            }
                        }

                    }
                }
            }


    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                when {
                    (viewBinding.drawer.isDrawerOpen(GravityCompat.START)) -> {
                        viewBinding.drawer.closeDrawers()
                        true
                    }
                    (System.currentTimeMillis() - lastBackTime > 2000) -> {
                        R.string.toast_exit_app
                            .getString()
                            .showSnackBar(viewBinding.editorRoot)
                        lastBackTime = System.currentTimeMillis()
                        true
                    }
                    else -> super.onKeyUp(keyCode, event)
                }
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DrawablePool.clear()
        presenter.saveAllFile()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_toolbar, menu)
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
            R.id.editor_action_save -> {
                presenter.saveAllFile()
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