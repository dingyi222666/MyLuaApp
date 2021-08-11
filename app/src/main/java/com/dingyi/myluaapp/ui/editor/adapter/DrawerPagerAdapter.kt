package com.dingyi.myluaapp.ui.editor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author: dingyi
 * @date: 2021/8/11 4:11
 * @description:
 **/
class DrawerPagerAdapter(context: FragmentActivity) : FragmentStateAdapter(context) {

    private val classes = mutableListOf<Class<*>>()

    override fun getItemCount(): Int {
        return classes.size
    }

    override fun createFragment(position: Int): Fragment {
        return classes[position].newInstance() as Fragment
    }

    fun add(vararg arg: Class<*>) {
        classes.addAll(arg)
        notifyDataSetChanged()
    }

}
