package com.dingyi.MyLuaApp.widget.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.databinding.FragmentFileListAdapterBinding
import com.dingyi.MyLuaApp.databinding.FragmentFileTreeAdapterBinding
import com.dingyi.MyLuaApp.utils.dp2px
import com.dingyi.MyLuaApp.utils.getImageType
import com.dingyi.MyLuaApp.utils.hasChildFile
import com.dingyi.MyLuaApp.utils.toFile

class FileTreeView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {


    var rootPath = ""

        set(value) {
            field = value
            startShowTree()
        }

    private val adapter = FileTreeViewAdapter()


    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setAdapter(adapter)
    }

    private fun startShowTree() {
        rootPath.toFile().listFiles().forEach {
            val node=Node(0,it.path,it.name,it.isDirectory,false)
            adapter.add(node)
        }
    }

    data class Node(val deep: Int,
                    val path: String,
                    val name: String,
                    val isDir: Boolean = false,
                    val isOpen: Boolean = false)

    class FileTreeViewAdapter : RecyclerView.Adapter<FileTreeViewAdapter.ViewHolder>() {

        private var data = mutableListOf<Node>()

        inner class ViewHolder(val binding: FragmentFileTreeAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(FragmentFileTreeAdapterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            ))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val node = data[position]
            val binding = holder.binding
            binding.title.text = node.name
            binding.now.setImageResource(getImageType(node.path.toFile()))
            binding.next.animate()
                    .rotation(if (node.isOpen) 45f else 0f)
                    .setDuration(150)
                    .start()
            binding.next.visibility = if (node.isDir && node.path.toFile().hasChildFile()) View.VISIBLE else View.INVISIBLE
            binding.root.x = binding.root.context.dp2px(if (node.deep > 10) 10 * 12 + (node.deep - 10) * 4 else node.deep * 12).toFloat()
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun add(node: Node) {
            data.add(node)
            notifyItemInserted(data.size - 1)
        }


    }
}