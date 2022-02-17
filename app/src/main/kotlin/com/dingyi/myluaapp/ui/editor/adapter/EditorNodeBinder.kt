package com.dingyi.myluaapp.ui.editor.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.dp
import com.dingyi.myluaapp.common.kts.sortBySelf
import com.dingyi.myluaapp.common.kts.suffix
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.databinding.LayoutItemEditorFileListBinding
import com.dingyi.view.treeview.TreeNode
import com.dingyi.view.treeview.TreeView
import com.dingyi.view.treeview.base.BaseNodeViewBinder
import java.io.File

class EditorNodeBinder(itemView: View) : BaseNodeViewBinder(itemView) {

    private lateinit var binding: LayoutItemEditorFileListBinding

    override fun bindView(treeNode: TreeNode) {
        val file = treeNode.value as File

        binding = LayoutItemEditorFileListBinding.bind(itemView)
        binding.title.text = file.name

        Glide.with(binding.image)
            .load(getImageResId(file.path))
            .into(binding.image)

        itemView.layoutParams =
            ((itemView as ViewGroup).layoutParams as ViewGroup.MarginLayoutParams)
                .apply {
                    this.leftMargin = treeNode.level * 15.dp
                }

        binding.arrow.visibility = if (file.isFile) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

    }


    override fun onNodeToggled(treeNode: TreeNode, expand: Boolean) {

        val file = treeNode.value as File


        if (file.isFile) {

        } else {

            binding
                .arrow
                .animate()
                .rotation(if (expand) 90f else 0f)
                .setDuration(150)
                .start()

        }

    }


    private fun getChildTreeNode(file: File, treeNode: TreeNode): MutableList<TreeNode> {
        return file.listFiles()?.sortBySelf()?.map {
            TreeNode(it, treeNode.level + 1)
        }?.toMutableList() ?: mutableListOf()
    }

    companion object {
        private var imageData = mapOf(
            "lua,java,aly,gradle,xml,py" to R.drawable.ic_twotone_code_24,
            "default" to R.drawable.ic_twotone_insert_drive_file_24,
            "png,jpg,bmp" to R.drawable.ic_twotone_image_24,
            "dir" to R.drawable.ic_twotone_folder_24
        )

        private fun getImageResId(path: String): Int {
            val file = path.toFile()
            if (file.isDirectory)
                return imageData.getValue("dir")

            if (path == "...")
                return R.drawable.ic_twotone_undo_24


            imageData.forEach {
                if (it.key.lastIndexOf(file.suffix) != -1) {
                    return it.value
                }
            }

            return imageData.getValue("default")
        }
    }

}