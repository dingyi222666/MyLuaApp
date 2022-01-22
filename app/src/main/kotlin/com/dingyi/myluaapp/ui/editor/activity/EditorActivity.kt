package com.dingyi.myluaapp.ui.editor.activity

import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.postDelayed
import androidx.drawerlayout.widget.DrawerLayout
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.addLayoutTransition
import com.dingyi.myluaapp.common.kts.addDrawerListener
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.getPrivateField
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.adapter.EditorDrawerPagerAdapter
import com.dingyi.myluaapp.ui.editor.adapter.EditorPagerAdapter


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
        viewBinding.toolbar.getPrivateField<TextView>("mTitleTextView").apply {
            transitionName =
                "project_name_transition"

            ellipsize = TextUtils.TruncateAt.END

        }

        startPostponedEnterTransition()



        viewModel.refreshOpenedFile()


    }


    private fun initView() {

        viewBinding.editorPage.adapter = EditorPagerAdapter(this, viewModel)

        viewBinding.editorPage.offscreenPageLimit = 1

        viewBinding.editorTab.apply {
            postDelayed(    20) {
                bindEditorPager(viewBinding.editorPage)

                viewBinding.drawerPage.adapter = EditorDrawerPagerAdapter(this@EditorActivity).apply {
                    notifyDataSetChanged()
                }

            }
            projectPath = viewModel.controller.projectPath
            onSelectFile {
                viewModel.controller.selectOpenedFile(it)
            }
            onCloseFile {
                viewModel.controller.closeFile(it)
                viewModel.refreshOpenedFile()
            }
            onCloseOtherFile {
                viewModel.controller.closeOtherFile(it)
                //清空下
                viewModel.refreshOpenedFile()
            }

        }


        listOf(viewBinding.container,viewBinding.toolbar).forEach {
            it.addLayoutTransition() }



        supportActionBar?.let { actionBar ->
            viewBinding.editorTab.bindActionBar(actionBar)
            actionBar.setDisplayHomeAsUpEnabled(true)
            val toggle = ActionBarDrawerToggle(this, viewBinding.drawer, 0, 0)

            viewBinding.drawer.addDrawerListener(toggle)
            toggle.syncState()
        }
        viewBinding.drawer.apply {
            setScrimColor(0)
            drawerElevation = 0f
            addDrawerListener(
                onDrawerClosed = {
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                },
                onDrawerOpened = {
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                },
                onDrawerSlide = { _, slideOffset ->
                    viewBinding.main.x = viewBinding.drawerPage.width * slideOffset
                }
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_toolbar, menu)
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

    override fun observeViewModel() {

        viewModel.appTitle.observe(this) {
            supportActionBar?.title = it
        }

        viewModel.openFiles.observe(this) { pair ->
            val list = pair.first

            val visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
            arrayOf(
                viewBinding.editorPage,
                viewBinding.editorTab,

            ).forEach {
                it.visibility = visibility
            }
            viewBinding.editorToastOpenFile.visibility =
                if (list.isEmpty()) {
                    viewBinding.toolbar.subtitle = null
                    View.VISIBLE
                } else View.GONE

            viewBinding.editorTab.postOpenedFiles(list, pair.second)
            viewBinding.editorPage.adapter?.notifyDataSetChanged()
        }

    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                when {
                    (viewBinding.drawer.isDrawerOpen(GravityCompat.START)) -> {
                        viewBinding.drawer.closeDrawers()
                        true
                    }
                    else -> super.onKeyUp(keyCode, event)
                }
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }
}