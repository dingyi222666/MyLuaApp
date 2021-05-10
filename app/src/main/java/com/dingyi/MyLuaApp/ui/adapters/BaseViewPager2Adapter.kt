package com.dingyi.MyLuaApp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dingyi.MyLuaApp.base.BaseActivity

class BaseViewPager2Adapter(val activity: BaseActivity<*>) : FragmentStateAdapter(activity) {

    private val views = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return views[position]
    }

    override fun getItemCount(): Int {
        return views.size
    }

    fun add(view: Fragment) {
        views.add(view)
        notifyDataSetChanged()
    }

    fun <T> getFragment(index: Int): T {
        val fragment = views[index]
        return fragment as T
    }
}