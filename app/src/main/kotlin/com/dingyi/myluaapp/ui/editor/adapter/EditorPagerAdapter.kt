package com.dingyi.myluaapp.ui.editor.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.ui.editor.fragment.EditorFragment

class EditorPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemId(position: Int): Long {
        return (differ.currentList.getOrNull(position)?.getId() ?: position).toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return differ.currentList.map { it.getId() }.contains(itemId.toInt())
    }

    private val differ = AsyncListDiffer(this, DiffItemCallBack)

    object DiffItemCallBack :
        DiffUtil.ItemCallback<Editor>() {


        override fun areItemsTheSame(oldItem: Editor, newItem: Editor): Boolean {
            return oldItem.getFile().path == newItem.getFile().path
        }

        override fun areContentsTheSame(oldItem: Editor, newItem: Editor): Boolean {
            return oldItem.getFile().path == newItem.getFile().path
        }


    }

    fun submitList(list: List<Editor>) {
        differ.submitList(list)
    }

    override fun createFragment(position: Int): Fragment {
        return EditorFragment.newInstance(
            bundle = Bundle().apply {
                putString("editor_page_path", differ.currentList[position].run {
                    getFile().path
                })
            }
        )
    }
}