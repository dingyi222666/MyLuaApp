package com.dingyi.myluaapp.view


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.ActionBar

import androidx.viewpager2.widget.ViewPager2
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.core.project.ProjectFile
import com.dingyi.myluaapp.ui.editor.adapter.EditorPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.io.println
import kotlin.properties.Delegates

class EditorTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {


    var projectPath = ""

    private var editorPage: ViewPager2? = null

    private var openedFileList = listOf<ProjectFile>()

    private var actionBar by Delegates.notNull<ActionBar>()


    private val tabNameList = mutableListOf<String>()

    val onSelectFile = { callback: (String) -> Unit ->
        callbackList[0x01] = callback
    }

    val onCloseFile = { callback: (String) -> Unit ->
        callbackList[0x02] = callback
    }

    val onCloseOtherFile = { callback: (String) -> Unit ->
        callbackList[0x03] = callback
    }

    private val callbackList = mutableMapOf<Int, Any>()


    fun postOpenedFiles(list: List<ProjectFile>, nowOpenedFile: String) {


        openedFileList = list

        list.map {
            getTabText(it)
        }.addAllTo(tabNameList)


        if (list.isEmpty()) {
            actionBar.subtitle = null
            return
        }

        list.forEachIndexed { index, projectFile ->
            if (projectFile.path == nowOpenedFile) {
                actionBar.subtitle = getTabText(projectFile)

                (editorPage?.adapter as EditorPagerAdapter?)
                    ?.submitList(list)

                editorPage?.setCurrentItem(index, true)

                return
            }
        }


    }

    private fun getTabIndex(tab: Tab): Int {
        //如果确定位置对 就不用更新tab位置
        if (getTabAt(tab.position) == tab) {
            return tab.position
        }
        //否则就更新一次tab位置
        for (i in 0 until tabCount) {
            getTabAt(i)?.setPrivateField<Tab>("position", i)

        }
        return tab.position

    }


    private fun getTabText(projectFile: ProjectFile): String {
        return projectFile.path.substring(projectPath.length + 1)
    }

    fun bindActionBar(actionBar: ActionBar) {
        this.actionBar = actionBar
    }

    private fun generateTab(tab: Tab): Tab {
        return tab.apply {
            view.setOnLongClickListener { view ->
                R.menu.editor_tab.showPopMenu(view) { menu ->
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.editor_action_close_other -> {
                                val index = getTabIndex(this)
                                val deleteProjectFile = openedFileList[index]

                                runCatching {
                                    callbackList[0x03]?.convertObject<(String) -> Unit>()
                                        ?.invoke(
                                            deleteProjectFile.path
                                        )
                                }
                            }
                            R.id.editor_action_close -> {
                                val index = getTabIndex(this)
                                val deleteProjectFile = openedFileList[index]


                                runCatching {
                                    callbackList[0x02]?.convertObject<(String) -> Unit>()
                                        ?.invoke(
                                            deleteProjectFile.path
                                        )
                                }

                            }
                        }
                        true
                    }
                }
                true
            }
        }
    }

    override fun selectTab(tab: Tab?) {
        super.selectTab(tab)

        println("now indexed ${tab?.position} ${editorPage?.adapter} ${editorPage?.adapter?.itemCount}")


        if (openedFileList.isNotEmpty() && editorPage?.adapter != null) {

            val position = tab?.position ?: editorPage?.currentItem ?: 0


            actionBar.subtitle = getTabText(openedFileList[position])

            runCatching {
                callbackList[0x01]?.convertObject<(String) -> Unit>()
                    ?.invoke(openedFileList[position].path)
            }

        }
        //不知道为什么有效


    }

    override fun newTab(): Tab {
        val tab = super.newTab()
        return generateTab(tab)
    }

    fun bindEditorPager(editorPage: ViewPager2) {

        this.editorPage = editorPage

        TabLayoutMediator(
            this, editorPage
        ) { tab, position -> // Styling each tab here
            tab.text = tabNameList.getOrElse(position) {
                tab.text
            }


        }.attach()


    }


}