package com.dingyi.MyLuaApp.ui.fragments

import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager

import com.dingyi.MyLuaApp.core.project.helper.ProjectFileHelper
import com.dingyi.MyLuaApp.databinding.FragmentFileTreeBinding
import kotlin.properties.Delegates

class FileTreeFragment(mActivity: BaseActivity<*>) :
    BaseFragment<FragmentFileTreeBinding>(mActivity), ProjectFileHelper.RefreshCallBack {

    private var mBinding by Delegates.notNull<FragmentFileTreeBinding>()

    private var mEditManager by Delegates.notNull<EditorManager>()

    private var mProjectInfo by Delegates.notNull<ProjectInfo>()

    var rootPath = ""
        set(value) {
            field = value
            mBinding.tree.rootPath = value
            mBinding.title.text = value
        }

    override fun getViewBindingClass(): Class<FragmentFileTreeBinding> {
        return FragmentFileTreeBinding::class.java
    }

    override fun initView(binding: FragmentFileTreeBinding?) {
        binding?.let {
            this.mBinding = it
        }


    }

    fun initView(manager: EditorManager, info: ProjectInfo) {
        rootPath = info.projectPath

    }

    override fun onRefresh(path: String) {

    }
}