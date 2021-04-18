package com.dingyi.MyLuaApp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.databinding.FragmentFileListAdapterBinding
import com.dingyi.MyLuaApp.utils.getImageType
import com.dingyi.MyLuaApp.utils.getSuffix
import com.dingyi.MyLuaApp.utils.toFile
import java.io.File

class FileListAdapter(private val info: ProjectInfo) : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentFileListAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val data = mutableListOf<String>()

    var onClickListener: (String) -> Unit = {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentFileListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text= {
            if (data[position] != "...") {
                data[position].toFile().name
            } else {
                data[position]
            }
        }.invoke().toString()

        holder.binding.title.text =text
        holder.binding.icon.setImageResource(getImageType(data[position].toFile()))
        holder.binding.root.setOnClickListener {
            onClickListener(holder.binding.title.text.toString())
        }

    }

    fun addAll(list: List<String>) {
        data.clear()
        notifyDataSetChanged()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}