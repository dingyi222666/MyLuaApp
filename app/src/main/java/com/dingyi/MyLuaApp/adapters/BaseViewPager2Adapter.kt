package com.dingyi.MyLuaApp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class BaseViewPager2Adapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    val data = mutableListOf<View>()
    val holders= mutableListOf<FragmentViewHolder>()
    var context: Context? = null;

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment{
        return BaseFragment(data[position])
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holders.add(position,holder)
    }

    fun add(view: View) {
        data.add(view)
        notifyDataSetChanged()
    }

    fun remove(i:Int) {
        onViewRecycled(holders[i])
        holders.removeAt(i)
    }

    class BaseFragment(private val itemView: View): Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return itemView
        }
    }



}