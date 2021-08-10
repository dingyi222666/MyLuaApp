package com.dingyi.myluaapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.myluaapp.ui.editor.MainViewModel

/**
 * @author: dingyi
 * @date: 2021/8/11 4:11
 * @description:
 **/
class EditorDrawerPagerAdapter(context: FragmentActivity) : FragmentStateAdapter(context) {

    private val classes= mutableListOf<Class<Fragment>>()

    override fun getItemCount(): Int {
       return classes.size
    }

    override fun createFragment(position: Int): Fragment {
        return classes[position].newInstance()
    }

}
