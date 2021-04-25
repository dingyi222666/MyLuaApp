package com.dingyi.MyLuaApp.ui.fragments

import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager
import com.dingyi.MyLuaApp.databinding.FragmentFileTreeBinding
import com.dingyi.MyLuaApp.widget.views.FileTreeView
import kotlin.properties.Delegates

class FileTreeFragment: BaseFragment<FragmentFileTreeBinding>() {

    private var mBinding by Delegates.notNull<FragmentFileTreeBinding>()

    private var mEditManager by Delegates.notNull<EditorManager>()

    private var mProjectInfo by Delegates.notNull<ProjectInfo>()

    var rootPath = ""
        set(value) {
            field = value
            mBinding.tree.rootPath=value
            mBinding.title.text=value
        }

    override fun getViewBindingClass(): Class<FragmentFileTreeBinding> {
       return FragmentFileTreeBinding::class.java
    }

    override fun initView(binding: FragmentFileTreeBinding?) {
        binding?.let {
            this.mBinding=it
        }


    }

    fun initView(manager: EditorManager, info: ProjectInfo) {
        rootPath=info.projectPath
        mBinding.tree.defaultOpenFileCallback=object : FileTreeView.OpenFileCallback {
            override fun onOpenFile(path: String) {
                manager.open(path)
            }

        }
    }
}