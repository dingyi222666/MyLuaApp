package com.dingyi.myluaapp.view

import android.content.Context
import android.util.AttributeSet

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.core.project.ProjectFile
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates

class EditorTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {


    var project by Delegates.notNull<Project>()

    private var oldOpenedFileList = listOf<ProjectFile>()

    fun postOpenedFiles(list: List<ProjectFile>) {

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
                for (i in position until (position+count)) {
                    addTab(generateTab(getTabText(list[position])),i)
                }
            }

            override fun onRemoved(position: Int, count: Int) {
                for (i in position until (position+count)) {
                    removeTabAt(position)
                }
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
               getTabAt(toPosition)?.text = getTabText(list[toPosition])
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {

                getTabAt(position)?.text = getTabText(list[position])
            }

        })

        oldOpenedFileList = list

    }

    private fun getTabText(projectFile: ProjectFile): String {
        return projectFile.path.substring(project.projectPath.length+1)
    }

    private fun generateTab(text:String): Tab {
        return newTab().apply {
            this.text = text
        }
    }

}