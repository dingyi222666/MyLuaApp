package com.dingyi.myluaapp.ui.editor.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.core.project.ProjectFile
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.fragment.EditorFragment

class EditorPagerAdapter(fragmentActivity: FragmentActivity, private val viewModel: MainViewModel) :
    FragmentStateAdapter(fragmentActivity) {

    private var data = listOf<ProjectFile>()

    override fun getItemCount(): Int {
        return viewModel.openFiles.value?.run {
            first.size
        } ?: 0
    }

    override fun getItemId(position: Int): Long {
        return (viewModel.openFiles.value?.first?.get(position)?.hashCode() ?: position).toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return viewModel.openFiles.value?.first?.any { it.path.hashCode().toLong() == itemId }
            ?: true
    }

    fun submitList(list: List<ProjectFile>) {

        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return data.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return data[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return data[oldItemPosition] == list[newItemPosition]
            }

        }).dispatchUpdatesTo(this)

        data = list

    }

    override fun createFragment(position: Int): Fragment {
        return EditorFragment.newInstance(
            bundle = Bundle().apply {
                putString("editor_page_path", viewModel.openFiles.value?.run {
                    first[position].path
                })
            }
        )
    }
}