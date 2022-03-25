package com.dingyi.myluaapp.ui.editor.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.common.ktx.*
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.adapter.EditorDrawerPagerAdapter
import com.dingyi.myluaapp.ui.editor.adapter.EditorPagerAdapter
import com.dingyi.myluaapp.ui.editor.helper.ActionHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class EditorActivity : BaseActivity<ActivityEditorBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }

    private lateinit var tabLayoutMediator: TabLayoutMediator
    private var isCreated = false

    private var isCallMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {

        //先不启用过渡动画
        postponeEnterTransition()

        super.onCreate(savedInstanceState)


        setSupportActionBar(viewBinding.toolbar)


        viewModel.openProject((intent.getStringExtra("project_path") ?: "").toFile())


        supportActionBar?.title = viewModel.project.value?.name.toString()


        //反射获取控件和启用过渡动画
        viewBinding.toolbar.getPrivateField<TextView>("mTitleTextView").apply {
            transitionName =
                "project_name_transition"

            ellipsize = TextUtils.TruncateAt.END


        }


        startPostponedEnterTransition()

        viewModel.logBroadcastReceiver.value = LogBroadcastReceiver(lifecycle, this)


        ActionHelper
            .initEditorActivity(this)


        initView()


        syncProject()


        isCreated = true

    }


    private suspend fun initViewAfterSync() {

        viewModel.initEditor()

        viewModel.refreshFileList()

    }

    private fun syncProject() {

        lifecycleScope.launch {

            PluginModule
                .getActionService()
                .callAction<Unit>(
                    PluginModule
                        .getActionService()
                        .createActionArgument(), DefaultActionKey.BUILD_STARTED_KEY
                )

            delay(20)

            viewModel.project.value?.let {
                PluginModule
                    .getBuildService<Service>()
                    .build(it, "sync")
            }
        }


    }



    private fun updateTab(tab: TabLayout.Tab, index: Int, choose: Boolean = false) {
        val currentEditor = viewModel.allEditor.value?.getOrNull(index)

        val name = currentEditor?.getFile()?.name ?: "Unknown"

        /*
        if (currentEditor?.isModified() == true) {
            name = "*$name"
        } */


        //抖动
        if (tab.text != name) {
            tab.text = name
        }

        if (choose) {
            viewModel.allEditor.value?.getOrNull(index)?.getFile()?.path?.let {
                viewModel.setCurrentEditor(it)
            }
        }

    }

    private fun getCurrentEditor(tab: TabLayout.Tab?): Editor? {
        return tab?.let {
            viewModel.allEditor.value?.getOrNull(it.position)
        }

    }

    private fun initView() {

        viewBinding.editorPage.adapter = EditorPagerAdapter(this)
        viewBinding.drawerPage.offscreenPageLimit = 1
        viewBinding.editorPage.isUserInputEnabled = false


        viewBinding
            .drawerPage
            .adapter = EditorDrawerPagerAdapter(this@EditorActivity).apply {
            notifyItemRangeInserted(0, viewBinding.drawerPage.adapter?.itemCount ?: 2)
        }

        tabLayoutMediator = TabLayoutMediator(
            viewBinding.editorTab,
            viewBinding.editorPage, false
        ) { tab, index ->
            updateTab(tab, index)
        }
        tabLayoutMediator.attach()


        viewBinding
            .editorTab
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        updateTab(it, it.position, true)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) { getCurrentEditor(tab)?.save() }
                    }

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    PluginModule.getActionService()
                        .callAction<Unit>(
                            PluginModule.getActionService().createActionArgument()
                                .addArgument(tab)
                                .addArgument(getCurrentEditor(tab))
                                .addArgument({
                                    viewModel.refreshEditor()
                                }), DefaultActionKey.SHOW_FILE_TAG_MENU
                        )

                }

            })




        listOf(
            viewBinding.appbarLayout,
            viewBinding.container,
            viewBinding.main
        ).forEach {
            it.addLayoutTransition()
        }


        viewBinding.symbolView.setOnClickListener {
            PluginModule.getActionService()
                .callAction<Unit>(
                    PluginModule.getActionService().createActionArgument()
                        .addArgument(it), DefaultActionKey.CLICK_SYMBOL_VIEW
                )
        }

        supportActionBar?.let { actionBar ->

            actionBar.setDisplayHomeAsUpEnabled(true)
            val toggle = ActionBarDrawerToggle(this, viewBinding.drawer, 0, 0)

            viewBinding.drawer.addDrawerListener(toggle)
            toggle.syncState()
        }
        viewBinding.drawer.apply {
            //setScrimColor(0)
            drawerElevation = 1f
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


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!isCallMenu) {

            val projectMenu = menu?.findItem(R.id.editor_action_project) as MenuItem

            PluginModule
                .getActionService()
                .callAction<Unit>(
                    PluginModule
                        .getActionService()
                        .createActionArgument()
                        .addArgument(viewModel.project.value)
                        .addArgument(projectMenu),
                    DefaultActionKey
                        .ADD_PROJECT_MENU
                )

            isCallMenu = true
        }


        return super.onPrepareOptionsMenu(menu)
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
            R.id.editor_action_save -> viewModel.saveEditor()
            R.id.editor_action_undo -> viewModel.currentEditor.value?.undo()
            R.id.editor_action_redo -> viewModel.currentEditor.value?.redo()
            R.id.editor_action_run -> viewModel.project.value?.runProject()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun observeViewModel() {
        super.observeViewModel()


        viewModel.progressMonitor
            .getProgressState()
            .observe(this) { boolean ->
                viewBinding.progress.isVisible = boolean
            }

        viewModel.allEditor.observe(this) { list ->

            arrayOf(
                viewBinding.editorPage,
                viewBinding.editorTab,
                viewBinding.symbolView
            ).forEach {
                it.isVisible = list.isNotEmpty()
            }

            viewBinding.editorToastOpenFile.isVisible = list.isEmpty()


            (viewBinding.editorPage.adapter as EditorPagerAdapter).let { adapter ->
                adapter.submitList(list.toList()) {
                    val index = list.indexOf(viewModel.currentEditor.value)
                    viewBinding.editorPage.post {
                        viewBinding.editorPage.setCurrentItem(
                            if (index == -1) viewBinding.editorPage.currentItem else index, false
                        )
                    }
                    tabLayoutMediator.javaClass
                        .getDeclaredMethod("populateTabsFromPagerAdapter")
                        .apply {
                            isAccessible = true
                        }
                        .invoke(tabLayoutMediator)


                }
            }

        }


        viewModel.subTitle.observe(this) { title ->
            if (title.isEmpty()) {
                supportActionBar?.subtitle = null
            } else {
                supportActionBar?.subtitle = title
            }
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        PluginModule
            .init()
        PluginModule
            .loadAllPlugin()
    }

    override fun onPause() {
        super.onPause()

        if (this.isCreated) {
            
            viewModel.progressMonitor.runAfterTaskRunning {
                withContext(Dispatchers.IO) {
                    PluginModule
                        .getEditorService()
                        .saveEditorServiceState()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (this.isCreated) {

            viewModel.progressMonitor.runAfterTaskRunning(this::initViewAfterSync)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                PluginModule
                    .getEditorService()
                    .apply {
                        //saveEditorServiceState()
                        clearAllEditor()
                    }

            }



            tabLayoutMediator.detach()

            viewModel.progressMonitor.close()
        }

    }

}