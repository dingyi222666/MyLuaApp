package com.dingyi.MyLuaApp.widget.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.databinding.FragmentFileListAdapterBinding
import com.dingyi.MyLuaApp.databinding.FragmentFileTreeAdapterBinding
import com.dingyi.MyLuaApp.utils.*
import kotlin.properties.Delegates

class FileTreeView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    var rootPath = ""
        set(value) {
            field = value
            startShowTree()
        }

    private val adapter = FileTreeViewAdapter(this, context)

    var defaultOpenFileCallback=object : OpenFileCallback {
        override fun onOpenFile(path: String) {

        }

    }

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setAdapter(adapter)
    }

    private fun startShowTree() {
        val list = mutableListOf<Node>()
        rootPath.toFile().listSortFiles().forEach {
            val node = Node(0, it.path, it.name, it.isDirectory, false)
            list.add(node)
        }

        adapter.addAll(list)
    }

    data class Node(val deep: Int,
                    val path: String,
                    val name: String,
                    val isDir: Boolean = false,
                    var isOpen: Boolean = false) {
        override fun toString(): String {
            return "Node(deep=$deep, path='$path', name='$name', isDir=$isDir, isOpen=$isOpen)"
        }
    }

    class FileTreeViewAdapter(private val treeView: FileTreeView, private val context: Context) : RecyclerView.Adapter<FileTreeViewAdapter.ViewHolder>() {

        private val data = mutableListOf<Node>()

        class ViewHolder(val binding: FragmentFileTreeAdapterBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = FragmentFileTreeAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
            if (binding.root.parent != null && binding.root.parent is ViewGroup) {//不知道为啥初始化出来的一定有parent，那就删掉吧
                (binding.root.parent as ViewGroup).removeView(binding.root)
            }
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val path = data[position].path
            var node = getNodeByPath(path)
            val binding = holder.binding
            binding.title.text = node.name
            binding.now.setImageResource(getImageType(node.path.toFile()))

            binding.next.animate()
                    .rotation(if (node.isOpen) 90f else 0f)
                    .setDuration(150)
                    .start()

            binding.next.visibility = if (node.isDir && node.path.toFile().hasChildFile()) View.VISIBLE else View.INVISIBLE

            node = getNodeByPath(path)
            val paddingLeft = binding.root.context.dp2px(if (node.deep > 10) 10 * 12 + (node.deep - 10) * 4 else node.deep * 12).toFloat()



            if (binding.root.layoutParams is LinearLayout.LayoutParams) {
                (binding.root.layoutParams as LinearLayout.LayoutParams).leftMargin = paddingLeft.toInt()
            } else if (binding.root.layoutParams is LayoutParams) {
                (binding.root.layoutParams as LayoutParams).leftMargin = paddingLeft.toInt()
            }

            binding.root.setOnClickListener {
                node = getNodeByPath(path)
                val pos = getPositionByNode(node)
                if (node.isDir && !node.isOpen) {
                    treeView.openFileDir(node, pos)
                    binding.next.animate()
                            .rotation(if (node.isOpen) 90f else 0f)
                            .setDuration(150)
                            .start()
                } else if (node.isDir && node.isOpen) {
                    val range = treeView.removeFileDir(node, pos, data)
                    if (range > 0) {
                        notifyItemRangeRemoved(pos + 1, range)
                    }
                    notifyItemRangeChanged(pos, data.size - pos)
                    node.isOpen = false
                    binding.next.animate()
                            .rotation(if (node.isOpen) 90f else 0f)
                            .setDuration(150)
                            .start()
                }else if (!node.isDir) {
                    treeView.defaultOpenFileCallback.onOpenFile(node.path)
                }
            }
        }


        private fun getNodeByPath(path: String): FileTreeView.Node {
            data.forEach {
                if (it.path == path) {
                    return it
                }
            }
            return Node(0, "", "")
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private fun getPositionByNode(node: Node): Int {
            data.forEachIndexed { index, value ->
                if (node.path == value.path) {
                    return index
                }
            }
            return 0
        }

        fun add(node: Node) {
            data.add(node)
            notifyDataSetChanged()
            //notifyItemInserted(data.size - 1)
        }

        fun addAll(node: List<Node>) {
            val old = data.size
            data.addAll(node)
            notifyItemChanged(old)
            notifyItemRangeInserted(old, data.size - old)
        }

        fun addAll(node: List<Node>, pos: Int) {
            data.addAll(pos, node)
            notifyItemChanged(pos)
            notifyItemRangeInserted(pos, node.size)
            notifyItemRangeChanged(pos, data.size - pos)
        }
    }


    private fun openFileDir(node: Node, position: Int) {
        val list = mutableListOf<Node>()
        node.isOpen = true
        node.path.toFile().listSortFiles().forEach {
            val nodes = Node(node.deep + 1, it.path, it.name, it.isDirectory, false)
            list.add(nodes)
        }

        adapter.addAll(list, position + 1)
    }

    private fun getNodeRange(node: Node, position: Int, data: MutableList<Node>): Int {
        var range = 0;
        var tmp by Delegates.notNull<Node>()
        while (true) {
            tmp = data[position + 1]
            if (tmp.deep > node.deep) {
                range++
            } else {
                break;
            }
        }
        return range
    }

    private fun removeFileDir(node: Node, position: Int, data: MutableList<Node>): Int {
        var range = 0;
        var tmp by Delegates.notNull<Node>()
        while (true) {
            tmp = data[position + 1]
            if (tmp.deep > node.deep) {
                data.removeAt(position + 1)
                range++
            } else {
                break;
            }
        }

        return range
    }

    interface OpenFileCallback {
        fun onOpenFile(path: String)
    }

}