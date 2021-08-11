package com.dingyi.myluaapp.ui.editor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.common.kts.layoutInflater
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.databinding.LayoutItemEditorFileListBinding

/**
 * @author: dingyi
 * @date: 2021/8/11 5:50
 * @description:
 **/
class FileListAdapter :
    ListAdapter<Pair<Int, String>, FileListAdapter.ViewHolder>(DiffItemCallback) {

    private var _onItemClickListener: (String) -> Unit = {}

    fun setOnItemClickListener(block: (String) -> Unit) {
        _onItemClickListener = block
    }

    inner class ViewHolder(val binding: LayoutItemEditorFileListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    companion object DiffItemCallback : DiffUtil.ItemCallback<Pair<Int, String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutItemEditorFileListBinding.inflate(
                parent.layoutInflater, parent, false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = currentList[position]

        val name = if (data.second != "...") {
            data.second.toFile().name
        } else {
            data.second
        }

        holder.binding.apply {
            title.text = name
            Glide.with(image)
                .load(data.first)
                .into(image)
            root.setOnClickListener {
                _onItemClickListener(data.second)
            }
        }

    }

}