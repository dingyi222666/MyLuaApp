package com.dingyi.myluaapp.ui.editor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.myluaapp.common.kts.layoutInflater
import com.dingyi.myluaapp.databinding.LayoutItemEditorCodeMavigationItemBinding

/**
 * @author: dingyi
 * @date: 2021/9/3 21:46
 * @description:
 **/
class CodeNavigationAdapter :
    ListAdapter<String, CodeNavigationAdapter.ViewHolder>(DiffItemCallback) {

    private var _onItemClickListener: (String) -> Unit = {}

    fun setOnItemClickListener(block: (String) -> Unit) {
        _onItemClickListener = block
    }

    inner class ViewHolder(val binding: LayoutItemEditorCodeMavigationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object DiffItemCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutItemEditorCodeMavigationItemBinding.inflate(
                parent.layoutInflater, parent, false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = currentList[position]

        holder.binding.apply {
            title.text = data
            root.setOnClickListener {
                _onItemClickListener(data)
            }
        }

    }

}