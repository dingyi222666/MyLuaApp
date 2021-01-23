package com.dingyi.MyLuaApp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.databinding.ActivityMainListProjectBinding
import com.dingyi.MyLuaApp.utils.getProjectTypeText
import com.google.android.material.card.MaterialCardView

class MainFileListAdapter(val context: Context): BaseAdapter() {
    private val data= mutableListOf<ProjectInfo>()

    var onClickListener= object : OnClickListener {
        override fun onClick(info: ProjectInfo) {

        }
    }

    override fun getCount(): Int{
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].hashCode().toLong()
    }




    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view=convertView
        val holder :ViewHolder?
        val info=data[position]

        if (convertView==null) {
            view=ActivityMainListProjectBinding.inflate(LayoutInflater.from(context),parent,false).root
            holder=ViewHolder(view)
            view.tag=holder
        } else {
           holder=(view?.tag as ViewHolder)
        }

        holder.card.setOnClickListener{
            onClickListener.onClick(data[position])
        }

        holder.title.text=info.name
        holder.type.text=getProjectTypeText(context,info.type)
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

    class ViewHolder(itemView:View) {
        var title=itemView.findViewById<TextView>(R.id.main_listview_title)
        var type=itemView.findViewById<TextView>(R.id.main_listview_type)
        val card=itemView.findViewById<MaterialCardView>(R.id.card)
    }


    interface OnClickListener {
        fun onClick(info:ProjectInfo)
    }
}