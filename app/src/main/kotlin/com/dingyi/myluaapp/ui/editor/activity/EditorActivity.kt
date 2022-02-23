package com.dingyi.myluaapp.ui.editor.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
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
import com.dingyi.myluaapp.ui.editor.action.OpenTreeFileAction
import com.dingyi.myluaapp.ui.editor.adapter.EditorDrawerPagerAdapter
import com.dingyi.myluaapp.ui.editor.adapter.EditorPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.FixTabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorActivity : BaseActivity<ActivityEditorBinding, MainViewModel>() {


    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityEditorBinding {
        return ActivityEditorBinding.inflate(layoutInflater)
    }

    private lateinit var tabLayoutMediator: FixTabLayoutMediator
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

                registerForwardAction(DefaultActionKey.OPEN_EDITOR_FILE_DELETE_TOAST) {
                    it.addArgument(viewBinding.root)
                }
            }
    }

    private fun updateTab(tab: TabLayout.Tab, index: Int, choose: Boolean = false) {
        var name = PluginModule
            .getEditorService()
            .getAllEditor().getOrNull(index)?.getFile()?.name ?: "Unknown"

        val currentEditor = viewModel.allEditor.value?.second
        if (currentEditor?.isModified() == true) {
            name = "*$name"
        }

        tab.text = name

        if (choose) {
            PluginModule
                .getEditorService()
                .getAllEditor().getOrNull(index)?.getFile()?.path?.let {
                    viewModel.openFile(it)
                }
        }

    }

    private fun getCurrentEditor(tab: TabLayout.Tab?): Editor? {
        return tab?.let {
            PluginModule
                .getEditorService()
                .getAllEditor()[it.position]
        }

    }

    private fun initView() {
        viewBinding.editorPage.adapter = EditorPagerAdapter(this)
        viewBinding.editorPage.isUserInputEnabled = false

        viewBinding
            .drawerPage
            .adapter = EditorDrawerPagerAdapter(this).apply {
            notifyDataSetChanged()
        }

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

        tabLayoutMediator = FixTabLayoutMediator(
            viewBinding.editorTab,
            viewBinding.editorPage, true, true
        ) { tab, index ->
            updateTab(tab, index)
        }
        tabLayoutMediator.attach()


        listOf(viewBinding.main,viewBinding.container, viewBinding.toolbar).forEach {
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observeViewModel() {
        super.observeViewModel()


        viewModel.progressMonitor.getProgressState().observe(this) { boolean ->
            viewBinding.progress.visibility = if (boolean) View.VISIBLE else View.INVISIBLE
        }

        viewModel.allEditor.observe(this) { pair ->

            val (list, editor) = pair

            val visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE

            arrayOf(
                viewBinding.editorPage,
                viewBinding.editorTab,
                viewBinding.symbolView
            ).forEach {
                it.visibility = visibility
            }

            viewBinding.editorToastOpenFile.visibility =
                if (list.isEmpty()) {
                    viewBinding.toolbar.subtitle = null
                    View.VISIBLE
                } else View.GONE




            (viewBinding.editorPage.adapter as EditorPagerAdapter).let { adapter ->

                adapter.submitList(list.toList())

                val index = list.indexOf(editor)


                if (viewBinding.editorPage.currentItem != index) {
                    viewBinding.editorPage.post {
                        viewBinding.editorPage.setCurrentItem(
                            if (index == -1) viewBinding.editorPage.currentItem else index, true
                        )

                    }
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