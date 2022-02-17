package com.dingyi.myluaapp.ui.editor.helper

import com.dingyi.myluaapp.common.kts.sortBySelf
import com.dingyi.view.treeview.TreeNode
import java.io.File

object TreeHelper {
     fun getAllNode(path: File?): TreeNode {

        val main = TreeNode(path)


        addChildNode(main, main.level + 1)

        return main

    }

     fun addChildNode(main: TreeNode, level: Int) {
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