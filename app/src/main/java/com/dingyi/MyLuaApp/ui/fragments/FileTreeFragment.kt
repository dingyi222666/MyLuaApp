package com.dingyi.MyLuaApp.ui.fragments

import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding
import com.dingyi.MyLuaApp.databinding.FragmentFileTreeBinding
import kotlin.properties.Delegates

class FileTreeFragment: BaseFragment<FragmentFileTreeBinding>() {

    private var binding by Delegates.notNull<FragmentFileTreeBinding>()

    private var manager by Delegates.notNull<EditorManager>()

    private var ino by Delegates.notNull<ProjectInfo>()

    var rootPath = ""
        set(value) {
            field = value
            binding.tree.rootPath=value
            binding.title.text=value
        }

    override fun getViewBindingClass(): Class<FragmentFileTreeBinding> {
       return FragmentFileTreeBinding::class.java
    }

    override fun initView(binding: FragmentFileTreeBinding?) {
        binding?.let {
            this.binding=it
        }



    }

    fun initView(manager: EditorManager, info: ProjectInfo) {

    }
}