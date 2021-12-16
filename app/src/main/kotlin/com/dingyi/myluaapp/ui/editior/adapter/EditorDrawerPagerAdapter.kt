package com.dingyi.myluaapp.ui.editior.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.ui.editior.fragment.EditorFileListFragment

class EditorDrawerPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentClassArray = arrayOf(EditorFileListFragment)

    override fun getItemCount(): Int {
        return fragmentClassArray.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentClassArray[position].newInstance(Bundle())
    }
}