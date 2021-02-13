package com.dingyi.MyLuaApp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseViewPager2Adapter: RecyclerView.Adapter<BaseViewPager2Adapter.ViewHolder>() {

    private val views=mutableListOf<View>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(views[viewType])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       //no to do
    }

    override fun getItemCount(): Int {
        return views.size
    }

    fun add(view: View) {
        views.add(view)
        notifyDataSetChanged()
    }
}