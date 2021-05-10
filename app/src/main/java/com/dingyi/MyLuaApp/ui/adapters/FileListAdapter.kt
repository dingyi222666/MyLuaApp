package com.dingyi.MyLuaApp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.databinding.FragmentFileListAdapterBinding
import com.dingyi.MyLuaApp.utils.getImageType
import com.dingyi.MyLuaApp.utils.toFile

class FileListAdapter(private val info: ProjectInfo) :
    RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentFileListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    var onLongClickListener: (String, View) -> Unit = { _, _ ->

    }
    private val mData = mutableListOf<String>()

    var onClickListener: (String) -> Unit = {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentFileListAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = {
            if (mData[position] != "...") {
                mData[position].toFile().name
            } else {
                mData[position]
            }
        }.invoke().toString()

        holder.binding.title.text = text
        holder.binding.icon.setImageResource(getImageType(mData[position].toFile()))
        holder.binding.root.setOnClickListener {
            onClickListener(holder.binding.title.text.toString())
        }
        holder.binding.root.setOnLongClickListener {
            onLongClickListener(holder.binding.title.text.toString(), it)
            return@setOnLongClickListener true
        }

    }

    fun addAll(list: List<String>) {
        mData.clear()
        notifyDataSetChanged()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}