package com.dingyi.myluaapp.ui.editior.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.ui.editior.MainViewModel
import com.dingyi.myluaapp.ui.editior.fragment.EditorFragment

class EditorPagerAdapter(fragmentActivity: FragmentActivity, private val viewModel: MainViewModel) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return viewModel.openFiles.value?.run {
            println("size ${first.size}")
            first.size
        } ?: 0
    }

    override fun getItemId(position: Int): Long {
        return (runCatching {
            viewModel.openFiles.checkNotNull().value.checkNotNull().first[position].hashCode()
        }.getOrNull() ?: position).toLong()
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