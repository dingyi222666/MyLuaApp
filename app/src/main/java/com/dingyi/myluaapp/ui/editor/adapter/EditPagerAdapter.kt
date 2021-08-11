package com.dingyi.myluaapp.ui.editor.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.fragment.EditPagerFragment

/**
 * @author: dingyi
 * @date: 2021/8/8 21:50
 * @description:
 **/
class EditPagerAdapter(
    context: FragmentActivity,
    private val viewModel: MainViewModel
) : FragmentStateAdapter(context) {


    override fun getItemCount(): Int {
        return viewModel.projectConfig.value?.run {
            openFiles.size
        } ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return EditPagerFragment.newInstance(Bundle().apply {
            putString(
                "path",
                viewModel.projectConfig.value?.run { openFiles[position].filePath }.toString()
            )
        })
    }
}