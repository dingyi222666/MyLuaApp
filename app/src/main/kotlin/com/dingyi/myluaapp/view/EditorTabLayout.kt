package com.dingyi.myluaapp.view


import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.ActionBar

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.core.project.ProjectController
import com.dingyi.myluaapp.core.project.ProjectFile
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates

class EditorTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {


    var projectPath = ""

    private var editorPage by Delegates.notNull<ViewPager2>()

    private var oldOpenedFileList = listOf<ProjectFile>()

    private var actionBar by Delegates.notNull<ActionBar>()

    private var onSelectFileCallback: (String) -> Unit = {}

    val onSelectFile = { callback: (String) -> Unit ->
        onSelectFileCallback = callback
    }

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
                    addTab(generateTab(list[i].path.toFile().name), i)
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

        oldOpenedFileList = list

        println("nowOpenedFile $nowOpenedFile")

        list.forEachIndexed { index, projectFile ->
            if (projectFile.path == nowOpenedFile) {
                selectTab(getTabAt(index))
                actionBar.subtitle = getTabText(projectFile)
                editorPage.setCurrentItem(index, true)
                onSelectFileCallback(projectFile.path)
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
            override fun onTabSelected(tab: Tab?) {
                tab?.let { tab ->
                    val index = getTabIndex(tab)
                    if (oldOpenedFileList.isNotEmpty()) {
                        actionBar.subtitle = getTabText(oldOpenedFileList[index])
                        onSelectFileCallback(oldOpenedFileList[index].path)
                    }
                    editorPage.setCurrentItem(index, true)
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
        }
    }

    fun bindEditorPager(editorPage: ViewPager2) {
        this.editorPage = editorPage
    }


}