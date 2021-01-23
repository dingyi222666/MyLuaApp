package com.dingyi.MyLuaApp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.databinding.ActivityWelcomePermissionListBinding
import com.dingyi.MyLuaApp.utils.d

class WelcomePermissionAdapter(private val context: Context) : BaseAdapter() {

    private val data = mutableListOf<Bean>()

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }


    override fun getItemId(position: Int): Long {
        return data[position].hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = ActivityWelcomePermissionListBinding.inflate(LayoutInflater.from(context),parent,false).root
            view.tag = ViewHolder(view)
        }

        val tag=(view.tag as ViewHolder)
        val data=data[position]

        tag.icon.setImageResource(data.resourceInt)
        tag.description.text=data.description
        tag.title.text=data.title

        return view
    }


    fun add(title: String, description: String, resourceInt: Int) {
        data.add(Bean(title, description, resourceInt))
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val description = itemView.findViewById<TextView>(R.id.description)
    }

    data class Bean(val title: String, val description: String, val resourceInt: Int)


}