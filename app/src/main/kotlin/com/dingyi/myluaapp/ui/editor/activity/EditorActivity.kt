package com.dingyi.myluaapp.ui.editor.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.action.DeleteProjectFileAction
import com.dingyi.myluaapp.ui.editor.action.OpenLogFragmentAction
import com.dingyi.myluaapp.ui.editor.action.OpenTreeFileAction
import com.dingyi.myluaapp.ui.editor.action.TreeListOnLongClickAction
import com.dingyi.myluaapp.ui.editor.adapter.EditorDrawerPagerAdapter
import com.dingyi.myluaapp.ui.editor.adapter.EditorPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        initAction()

        initView()


        viewModel.progressMonitor.runAfterTaskRunning {
            lifecycleScope.launch {
                viewModel.initEditor()
            }
        }

        viewModel.logBroadcastReceiver.value = LogBroadcastReceiver(lifecycle, this)

        isCreated = true


    }

    private fun initAction() {
        PluginModule
            .getActionService().apply {
                registerAction(
                    getJavaClass<OpenTreeFileAction>(),
                    DefaultActionKey.CLICK_TREE_VIEW_FILE
                )
                registerAction(
                    getJavaClass<OpenLogFragmentAction>(),
                    DefaultActionKey.OPEN_LOG_FRAGMENT
                )
                registerAction(
                    getJavaClass<TreeListOnLongClickAction>(),
                    DefaultActionKey.TREE_LIST_ON_LONG_CLICK
                )
                registerAction(
                    getJavaClass<DeleteProjectFileAction>(),
                    DefaultActionKey.DELETE_PROJECT_FILE
                )
                registerForwardArgument(DefaultActionKey.DELETE_PROJECT_FILE) {
                    it.addArgument(this@EditorActivity)
                        .addArgument(viewModel)
                }
                registerForwardArgument(DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST) {
                    it.addArgument(viewBinding.root)
                }
                registerForwardArgument(DefaultActionKey.OPEN_LOG_FRAGMENT) {
                    it.addArgument(viewBinding)
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
        // viewBinding.editorPage.isUserInputEnabled = false


        viewBinding
            .drawerPage
            .adapter = EditorDrawerPagerAdapter(this).apply {
            notifyDataSetChanged()
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
            R.id.editor_action_run -> viewModel.project.value?.runProject()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun observeViewModel() {
        super.observeViewModel()


        viewModel.progressMonitor.getProgressState().observe(this) { boolean ->
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
                    viewBinding.editorPage.currentItem =
                        if (index == -1) viewBinding.editorPage.currentItem else index

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

            PluginModule
                .getEditorService()
                .saveEditorServiceState()
        }
    }

    override fun onResume() {
        super.onResume()

        if (this.isCreated) {
            lifecycleScope.launch {
                viewModel.initEditor()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()


        PluginModule
            .getEditorService()
            .clearAllEditor()

        tabLayoutMediator.detach()

    }

}