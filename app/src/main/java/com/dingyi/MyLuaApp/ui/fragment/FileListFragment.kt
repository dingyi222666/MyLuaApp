package com.dingyi.MyLuaApp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.edtior.EditorManager
import com.dingyi.MyLuaApp.core.project.ProjectManager
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding
import com.dingyi.MyLuaApp.ui.adapters.FileListAdapter
import com.dingyi.MyLuaApp.utils.toFile

object FileListFragment: Fragment() {

    @SuppressLint("StaticFieldLeak")
    var binding:FragmentFileListBinding?=null;

    private var info: ProjectInfo?=null;
    private var fileListAdapter= FileListAdapter()
    private var lastFilePath=""
    var onCreateViewFunction:()->Unit={

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding=FragmentFileListBinding.inflate(inflater,container,false);
        onCreateViewFunction();
        return binding?.root
    }


    fun initView(editorManager: EditorManager, info: ProjectInfo) {
        binding?.let { it ->
            it.list.adapter = fileListAdapter
            it.list.layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
            fileListAdapter.onClickListener={ path->
                val file=(info.path+"/"+path).toFile()
                if (path=="...") {
                    loadFileListData(info,lastFilePath.toFile().parentFile.parentFile.path)
                }else if(file.isDirectory){
                    loadFileListData(info,file.path)
                }else {
                    editorManager.open(file.path)
                }
            }
            loadFileListData(info,editorManager.projectManager.getLastOpenPath()!!.toFile().parentFile.path)
        }

    }


    private fun loadFileListData(info:ProjectInfo,path:String) {
        lastFilePath=path

        val fileList=mutableListOf<String>()

        path.toFile().listFiles().mapTo(fileList) {
            it.path.substring(info.path.length+1)
        }

        if (info.path!=path) {
            fileList.add(0,"...")
        }

        fileListAdapter.addAll(fileList)

    }

}