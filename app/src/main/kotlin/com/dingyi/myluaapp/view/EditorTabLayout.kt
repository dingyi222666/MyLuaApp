package com.dingyi.myluaapp.view


import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.PopupMenu

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.setPrivateField
import com.dingyi.myluaapp.common.kts.showPopMenu
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

    private var firstCurrentItem = false


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
                    addTab(generateTab(list[i].path.toFile().name), i, false)
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


        if (list.isEmpty()) {
            actionBar.subtitle = null
            return
        }

        list.forEachIndexed { index, projectFile ->
            if (projectFile.path == nowOpenedFile) {
                println("now indexed $index $projectFile ${editorPage?.adapter} ${editorPage?.adapter?.itemCount}")
                actionBar.subtitle = getTabText(projectFile)
                runCatching {
                    callbackList[0x01]?.convertObject<(String) -> Unit>()
                        ?.invoke(projectFile.path)
                }
                println(" tab ${getTabAt(index)?.text} ${getTabAt(index)?.position}")
                selectTab(getTabAt(index))
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
            Log.d(
                "fuck,bug",
                "name:${getTabAt(i)?.text},nowPosition:${i},tabPosition:${getTabAt(i)?.position}"
            )
        }
        return tab.position

    }

    init {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(selectTab: Tab?) {
                selectTab?.let { tab ->
                    val index = getTabIndex(tab)

                    editorPage?.setCurrentItem(index, true)

                    if (oldOpenedFileList.isNotEmpty()) {
                        actionBar.subtitle = getTabText(oldOpenedFileList[index])
                        runCatching {
                            callbackList[0x01]?.convertObject<(String) -> Unit>()
                                ?.invoke(oldOpenedFileList[index].path)
                        }
                        //不知道为什么有效
                    }

                }
            }

            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {
                this.onTabSelected(tab)
            }
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
            view.setOnLongClickListener { view ->
                R.menu.editor_tab.showPopMenu(view) { menu ->
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.editor_action_close_other -> {
                                val index = getTabIndex(this)
                                val deleteProjectFile = oldOpenedFileList[index]

                                runCatching {
                                    callbackList[0x03]?.convertObject<(String) -> Unit>()
                                        ?.invoke(
                                            deleteProjectFile.path
                                        )
                                }
                            }
                            R.id.editor_action_close -> {
                                val index = getTabIndex(this)
                                val deleteProjectFile = oldOpenedFileList[index]


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


    /**
     * A [ViewPager2.OnPageChangeCallback] class which contains the necessary calls back to the
     * provided [TabLayout] so that the tab position is kept in sync.
     *
     *
     * This class stores the provided TabLayout weakly, meaning that you can use [ ][ViewPager2.registerOnPageChangeCallback] without removing the
     * callback and not cause a leak.
     */
    private inner class ViewPagerCallback :
        ViewPager2.OnPageChangeCallback() {

        private var previousScrollState = 0
        private var scrollState = 0
        override fun onPageScrollStateChanged(state: Int) {
            previousScrollState = scrollState
            scrollState = state
        }

        init {
            scrollState = ViewPager2.SCROLL_STATE_IDLE
            previousScrollState = scrollState
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            // Only update the text selection if we're not settling, or we are settling after
            // being dragged
            val updateText =
                scrollState != ViewPager2.SCROLL_STATE_SETTLING || previousScrollState == ViewPager2.SCROLL_STATE_DRAGGING
            // Update the indicator if we're not settling after being idle. This is caused
            // from a setCurrentItem() call and will be handled by an animation from
            // onPageSelected() instead.
            val updateIndicator =
                !(scrollState == ViewPager2.SCROLL_STATE_SETTLING && previousScrollState == ViewPager2.SCROLL_STATE_IDLE)
            setScrollPosition(position, positionOffset, updateText, updateIndicator)

        }

        override fun onPageSelected(position: Int) {

            if (selectedTabPosition != position && position < tabCount) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                val updateIndicator = (scrollState == ViewPager2.SCROLL_STATE_IDLE
                        || (scrollState == ViewPager2.SCROLL_STATE_SETTLING
                        && previousScrollState == ViewPager2.SCROLL_STATE_IDLE))
                selectTab(getTabAt(position), updateIndicator)


            }

        }
    }

    fun bindEditorPager(editorPage: ViewPager2) {
        firstCurrentItem = true
        this.editorPage?.unregisterOnPageChangeCallback(viewPagerCallback)
        this.editorPage = editorPage
        editorPage.registerOnPageChangeCallback(viewPagerCallback)
    }


}