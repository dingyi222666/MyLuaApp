package com.dingyi.myluaapp.view


import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.PopupMenu

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.core.project.ProjectController
import com.dingyi.myluaapp.core.project.ProjectFile
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.properties.Delegates

class EditorTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {


    var projectPath = ""

    private var editorPage: ViewPager2? = null

    private var oldOpenedFileList = listOf<ProjectFile>()

    private var actionBar by Delegates.notNull<ActionBar>()

    private val viewPagerCallback = ViewPagerCallback()

    val onSelectFile = { callback: (String) -> Unit ->
        callbackList[0x01] = callback
    }

    val onCloseFile = { callback: (String) -> Unit ->
        callbackList[0x02] = callback
    }

    private val callbackList = mutableMapOf<Int,(String) -> Unit>()


    fun postOpenedFiles(list: List<ProjectFile>, nowOpenedFile: String) {

        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldOpenedFileList.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldOpenedFileList[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldOpenedFileList[oldItemPosition] == list[newItemPosition]
            }

        }).dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                for (i in position until (position + count)) {
                    addTab(generateTab(list[i].path.toFile().name), i,false)
                }
            }

            override fun onRemoved(position: Int, count: Int) {
                for (i in position until (position + count)) {
                    removeTabAt(position)
                }
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                getTabAt(toPosition)?.text = list[toPosition].path.toFile().name
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                for (i in position until (position + count)) {
                    getTabAt(i)?.text = list[i].path.toFile().name
                }

            }

        })


        println("nowOpenedFile $nowOpenedFile")

        oldOpenedFileList = list

        list.forEachIndexed { index, projectFile ->
            if (projectFile.path == nowOpenedFile) {
                println("now indexed $index $projectFile ${editorPage?.adapter} ${editorPage?.adapter?.itemCount}")
                actionBar.subtitle = getTabText(projectFile)
                callbackList[0x01]?.invoke(projectFile.path)
                getTabAt(index)?.select()
                println("now indexed test ${editorPage?.currentItem}")
                return
            }
        }


    }

    private fun getTabIndex(tab: Tab): Int {
        for (i in 0 until tabCount) {
            if (getTabAt(i) == tab) {
                return i
            }
        }
        return 0
    }

    init {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(selectTab: Tab?) {
                selectTab?.let { tab ->
                    val index = getTabIndex(tab)
                    if (oldOpenedFileList.isNotEmpty()) {
                        actionBar.subtitle = getTabText(oldOpenedFileList[index])
                        callbackList[0x01]?.invoke(oldOpenedFileList[index].path)
                        //不知道为什么有效
                        handler.postDelayed( {
                            editorPage?.currentItem = index
                        },10)
                    }
                }
            }
            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {}
        })
    }



    private fun getTabText(projectFile: ProjectFile): String {
        return projectFile.path.substring(projectPath.length + 1)
    }

    fun bindActionBar(actionBar: ActionBar) {
        this.actionBar = actionBar
    }

    private fun generateTab(text: String): Tab {
        return newTab().apply {
            this.text = text
            this.view.setOnLongClickListener {
                PopupMenu(context, it).apply {
                    inflate(R.menu.editor_tab)
                    show()
                }
                true
            }
        }
    }


    inner class ViewPagerCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            setScrollPosition(position,positionOffset,true,true)
        }

        override fun onPageSelected(position: Int) {
            actionBar.subtitle = getTabText(oldOpenedFileList[position])
        }

    }

    fun bindEditorPager(editorPage: ViewPager2) {
        this.editorPage?.unregisterOnPageChangeCallback(viewPagerCallback)
        this.editorPage = editorPage
        editorPage.registerOnPageChangeCallback(viewPagerCallback)
    }


}