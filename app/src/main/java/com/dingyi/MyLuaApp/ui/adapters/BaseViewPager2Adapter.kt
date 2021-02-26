package com.dingyi.MyLuaApp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.dingyi.MyLuaApp.base.BaseActivity

class BaseViewPager2Adapter(val activity: BaseActivity<*>): FragmentStateAdapter(activity) {

    private val views=mutableListOf<Fragment>()

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
}