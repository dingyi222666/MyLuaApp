package com.dingyi.MyLuaApp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.core.project.getProjectTypeText
import com.dingyi.MyLuaApp.databinding.ActivityMainListProjectBinding
import com.dingyi.MyLuaApp.ui.activitys.EditorActivity
import kotlin.properties.Delegates


class MainProjectListAdapter(private val context: Context) : BaseAdapter() {
    private val data = mutableListOf<ProjectInfo>()

    constructor(context: Context, data: List<ProjectInfo>) : this(context) {
        this.data.addAll(data)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): ProjectInfo {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].hashCode().toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder by Delegates.notNull<ViewHolder>()
        val info = data[position]

        if (convertView == null) {
            view = ActivityMainListProjectBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ).root
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = (view?.tag as ViewHolder)
        }

        (holder.itemView as ViewGroup).getChildAt(0).setOnClickListener {
            val intent = Intent(holder.itemView.context, EditorActivity::class.java)
            intent.putExtra("projectInfo", info)
            holder.itemView.context.startActivity(intent)
        }


        holder.title.text = info.projectName
        holder.type.text = getProjectTypeText(context, info.projectType)
        return view
    }

    fun add(info: ProjectInfo) {
        data.add(info)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(val itemView: View) {
        var title: TextView = itemView.findViewById(R.id.main_listview_title)
        var type: TextView = itemView.findViewById(R.id.main_listview_type)
    }

}