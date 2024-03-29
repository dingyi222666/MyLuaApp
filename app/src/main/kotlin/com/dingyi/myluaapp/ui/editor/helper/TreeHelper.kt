package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.ktx.sortFile
import com.dingyi.myluaapp.view.treeview.TreeNode
import java.io.File
import com.dingyi.myluaapp.view.treeview.helper.TreeHelper

object TreeHelper {
     fun getAllNode(path: File?): TreeNode {

        val main = TreeNode(path, 0)
        addChildNode(main, main.level + 1)

        return main

    }


    fun updateNode(node: TreeNode?): TreeNode? {

        if (node == null) {
            return null
        }

        if (!node.isLeaf) {
            updateNode(node.parent)
            return node
        }

        val allExpandPath = TreeHelper.getExpandNodes(node).map { it.value as File }

        TreeHelper.deleteAllChild(node)

        addChildNode(node, node.level + 1)



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
             ?.sortFile()?.forEach {
                 val node = TreeNode(
                     it,
                     level + 1
                 )
                 main.addChild(node)
             }

         main.isLeaf = true


         main.children.forEach {
             val file = it.value as File

             if (file.isDirectory) {
                 it.isLeaf = true
                 addChildNode(it, it.level + 1)
             }
         }
     }
}