package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.kts.sortBySelf
import com.dingyi.view.treeview.TreeNode
import com.dingyi.view.treeview.helper.TreeHelper
import java.io.File

object TreeHelper {
     fun getAllNode(path: File?): TreeNode {

        val main = TreeNode(path)


        addChildNode(main, main.level + 1)

        return main

    }


    fun updateNode(node:TreeNode?):TreeNode? {

        if (node == null) {
            return null
        }

        val allExpandPath = TreeHelper.getExpandNodes(node).map { it.value as File }

        TreeHelper.deleteAllChild(node)


        addChildNode(node,node.level+1)


        TreeHelper.getAllNodes(node)
            .filter { allExpandPath.contains(it.value as File) }
            .forEach {
                it.isExpanded = true
            }

        return node

    }

     private fun addChildNode(main: TreeNode, level: Int) {
        (main.value as File)
            .listFiles()
            ?.sortBySelf()?.forEach {
                val node = TreeNode(it, level + 1)
                main.addChild(node)
            }

        main.children.forEach {
            val file = it.value as File

            if (file.isDirectory) {
                addChildNode(it, it.level+1)
            }

        }
    }
}