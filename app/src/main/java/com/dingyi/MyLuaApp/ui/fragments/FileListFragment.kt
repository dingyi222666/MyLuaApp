package com.dingyi.MyLuaApp.ui.fragments


import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding
import com.dingyi.MyLuaApp.ui.adapters.FileListAdapter
import com.dingyi.MyLuaApp.utils.listSortFiles
import com.dingyi.MyLuaApp.utils.toFile
import kotlin.properties.Delegates

class FileListFragment: BaseFragment<FragmentFileListBinding>() {


    private var mFileListAdapter by Delegates.notNull<FileListAdapter>()
    private var mLastDirPath=""


    fun initView(editorManager: EditorManager, info: ProjectInfo) {
        viewBinding.let { it ->
            mFileListAdapter= FileListAdapter(info)
            it.list.adapter = mFileListAdapter
            it.list.layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
            mFileListAdapter.onClickListener={ path->
                val file=("$mLastDirPath/$path").toFile()
                if (path=="...") {
                    loadFileListData(info,mLastDirPath.toFile().parentFile.path)
                }else if(file.isDirectory){
                    loadFileListData(info,file.path)
                }else {
                    editorManager.open(file.path)
                }
            }
            loadFileListData(info,editorManager.mProjectManager.getLastOpenPath()!!.toFile().parentFile.path)
        }

    }


    private fun loadFileListData(info:ProjectInfo,path:String) {
        mLastDirPath=path
        val fileList=mutableListOf<String>()
        viewBinding?.let { binding ->
            binding.title.text=path
            path.toFile().listSortFiles().forEach {
                fileList.add(it.path)
            }
        }

        if (info.projectPath!=path) {
            fileList.add(0,"...")
        }

        mFileListAdapter.addAll(fileList)

    }

    override fun getViewBindingClass(): Class<FragmentFileListBinding> {
       return FragmentFileListBinding::class.java
    }

    override fun initView(binding: FragmentFileListBinding?) {

    }

}