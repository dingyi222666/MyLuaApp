package com.dingyi.myluaapp.ui.editor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.ui.editor.fragment.EditorBuildLogFragment
import com.dingyi.myluaapp.ui.editor.fragment.EditorFileListFragment

class EditorDrawerPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentClassArray = arrayOf(getJavaClass<EditorFileListFragment>(),
    getJavaClass<EditorBuildLogFragment>())

    override fun getItemCount(): Int {
        return fragmentClassArray.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentClassArray[position].newInstance()
    }
}