package com.dingyi.myluaapp.ui.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.common.kts.addDrawerListener
import com.dingyi.myluaapp.common.kts.addLayoutTransition
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.adapter.EditorPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
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

        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

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
                viewModel.projectConfig.value?.run {
                    val name = openFiles.run {
                        get(position).path.toFile().name
                    }.toString()
                    tab.text = name

                }
            }.attach()


            viewModel.editPagerTabText.addOnListChangedCallback(object :
                ObservableList.OnListChangedCallback<ObservableArrayList<MutableLiveData<String>>>() {
                override fun onChanged(sender: ObservableArrayList<MutableLiveData<String>>?) {

                }

                override fun onItemRangeChanged(
                    sender: ObservableArrayList<MutableLiveData<String>>?,
                    positionStart: Int,
                    itemCount: Int
                ) {

                }

                override fun onItemRangeInserted(
                    sender: ObservableArrayList<MutableLiveData<String>>?,
                    positionStart: Int,
                    itemCount: Int
                ) {
                    (positionStart..(positionStart + itemCount)).forEach {
                        sender?.get(it)?.observe(this@EditorActivity) { name ->
                            editorTab.getTabAt(it)?.text = name
                        }
                    }
                }

                override fun onItemRangeMoved(
                    sender: ObservableArrayList<MutableLiveData<String>>?,
                    fromPosition: Int,
                    toPosition: Int,
                    itemCount: Int
                ) {

                }

                override fun onItemRangeRemoved(
                    sender: ObservableArrayList<MutableLiveData<String>>?,
                    positionStart: Int,
                    itemCount: Int
                ) {
                    (positionStart..(positionStart + itemCount)).forEach {
                        sender?.get(it)?.removeObservers(this@EditorActivity)
                    }

                }


            })
        }


        presenter.openProject(projectInfo)


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeViewModel() {
        viewModel.apply {
            projectConfig.observe(this@EditorActivity) {
                lifecycleScope.launch {
                    viewBinding.let {
                        if (it.progress.visibility == View.VISIBLE) {
                            it.progress.visibility = View.GONE
                            it.editorTab.visibility = View.VISIBLE
                            it.editorPage.visibility = View.VISIBLE
                        }
                    }
                    (viewBinding.editorPage.adapter as EditorPagerAdapter)
                        .notifyDataSetChanged()
                }

            }
        }
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