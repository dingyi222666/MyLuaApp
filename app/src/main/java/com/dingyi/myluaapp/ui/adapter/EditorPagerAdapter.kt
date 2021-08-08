package com.dingyi.myluaapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.ui.editor.EditorPagerFragment
import com.dingyi.myluaapp.ui.editor.MainViewModel

/**
 * @author: dingyi
 * @date: 2021/8/8 21:50
 * @description:
 **/
class EditorPagerAdapter(
    private val context: FragmentActivity,
    private val viewModel: MainViewModel
) : FragmentStateAdapter(context) {


    override fun getItemCount(): Int {
        return viewModel.projectConfig.value?.run {
            openFiles.size
        } ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return EditorPagerFragment.newInstance(Bundle().apply {
            putString(
                "path",
                viewModel.projectConfig.value?.run { openFiles[position].path }.toString()
            )
            putInt("position",position)
        })
    }
}